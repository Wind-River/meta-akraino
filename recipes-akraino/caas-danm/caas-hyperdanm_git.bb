#
# Copyright (C) 2019 Wind River Systems, Inc.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

SUMMARY = "Containers as a Service hyperdanm component"
DESCRIPTION = "This contains the hyperdanm container image, and related deployment artifacts for the CaaS subsystem."
HOMEPAGE = "https://github.com/nokia/danm"

LICENSE = "Apache-2.0 & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93 \
                    file://danm/LICENSE;md5=eed9a83a00903ca2207bc3aeae6e9a43"

PROTOCOL = "https"
SRCREV_caas-danm = "a7fdbdf37816d2beced1bbe1cd6f2b24c207980f"
SRCREV_danm = "${DANM_VERSION}"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-danm;protocol=${PROTOCOL};name=caas-danm \
           git://github.com/nokia/danm.git;protocol=${PROTOCOL};name=danm;destsuffix=git/danm \
           https://nexus3.akraino.org/repository/rpm.snapshots/TA/release-1/rpms/x86_64/caas-hyperdanm-4.0.0-5.el7.centos.ta.x86_64.rpm;name=rpm-caas-hyperdanm;subdir=rpm-caas-hyperdanm \
"

SRC_URI[rpm-caas-hyperdanm.md5sum] = "476e76d22ba0627f149bfddd2d326ddd"
SRC_URI[rpm-caas-hyperdanm.sha256sum] = "cd5ad052060b6d230d543c63aa388bd67b2f83b30d7c853fab4d11b86b56cc3f"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "4.0.0"
MINOR_VERSION = "5"
DANM_VERSION = "7b0634aa1693c1d91ef5cfb025f2deda77941155"
GO_VERSION = "1.12.1"

binary_build_dir = "${B}/binary-save"
built_binaries_dir = "/binary-save"
danm_components = "netwatcher svcwatcher webhook"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "hyperdanm"
DANM_EXTRA_ARG = ' \
    --build-arg DANM_VERSION=${DANM_VERSION} \
    --build-arg go_version=${GO_VERSION} \
    --build-arg binaries=${built_binaries_dir} \
    --build-arg components="${danm_components}" \
'

do_compile () {
	# Build DANM binaries
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		${DANM_EXTRA_ARG} \
		--tag danm-builder:${IMAGE_TAG} \
		${docker_build_dir}/danm-builder

	builder_container=$(${docker_bin} run -id --rm --network=none --entrypoint=/bin/sh danm-builder:${IMAGE_TAG})
	mkdir -p ${binary_build_dir}/danm
	for component in ${danm_components}; do
		${docker_bin} cp ${builder_container}:${built_binaries_dir}/${component} ${binary_build_dir}/danm/
	done
	${docker_bin} rm -f ${builder_container}
	${docker_bin} rmi danm-builder:${IMAGE_TAG}

	# Build hyperdanm container image
	mkdir -p ${docker_build_dir}/${COMPONENT}/danm_binaries
	rsync -av ${binary_build_dir}/* ${docker_build_dir}/${COMPONENT}/danm_binaries

	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		--tag ${COMPONENT}:${IMAGE_TAG} \
		${docker_build_dir}/${COMPONENT}

	mkdir -p ${docker_save_dir}/
	${docker_bin} save ${COMPONENT}:${IMAGE_TAG} | xz -z -T2 > ${docker_save_dir}/${COMPONENT}:${IMAGE_TAG}.tar
	${docker_bin} rmi -f ${COMPONENT}:${IMAGE_TAG}
}

#####################################################################
# work around for docker build failre, we use the docker image from
# akraino repository directly.
#####################################################################
do_compile () {
	:
}

do_install () {
	install -d ${D}${caas_container_tar_path}
	rsync -rlpD ${WORKDIR}/rpm-caas-hyperdanm/${caas_container_tar_path}/${COMPONENT}:${IMAGE_TAG}.tar \
		${D}${caas_container_tar_path}
}

########### work around done #########

do_install_append () {
	install -d ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/danm_setup ${D}${roles_path}

	install -D ${S}/ansible/playbooks/danm_setup.yaml ${D}/${playbooks_path}/danm_setup.yaml
}

pkg_postinst_ontarget_${PN} () {
	mkdir -p ${postconfig_path}
	ln -s ${playbooks_path}/danm_setup.yaml ${postconfig_path}/
}

pkg_postrm_${PN} () {
	if [ $1 -eq 0 ]; then
		rm -f ${postconfig_path}/danm_setup.yaml
	fi
}

FILES_${PN} += "\
    ${roles_path} \
    ${playbooks_path} \
"
