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

SUMMARY = "Containers as a Service helm component"
DESCRIPTION = "This contains the helm container for CaaS subsystem."

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

PROTOCOL = "https"
SRCREV = "bce256cf51876e7bd62767d4102cf1ba56577665"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-helm;protocol=${PROTOCOL} \
           file://caas-helm-replace-git-with-tarball.patch \
"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "2.14.3"
MINOR_VERSION = "3"
GO_VERSION = "1.12.9"

binary_build_dir = "${B}/binary-save"
built_binaries_dir = "/binary-save"
centos_build = "191001"
build_arch = "x86_64"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "helm"
HELM_EXTRA_ARG = ' \
    --build-arg HELM_VERSION=${MAJOR_VERSION} \
    --build-arg go_version=${GO_VERSION} \
    --build-arg binaries=${built_binaries_dir} \
'

do_compile () {
	wget --progress=dot:giga \
		http://artifacts.ci.centos.org/sig-cloudinstance/centos-7-${centos_build}/${build_arch}/centos-7-${build_arch}-docker.tar.xz \
		-O ${docker_build_dir}/helm-builder/centos-7-docker.tar.xz
	# Build HELM binaries
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		${HELM_EXTRA_ARG} \
		--tag helm-builder:${IMAGE_TAG} \
		${docker_build_dir}/helm-builder

	mkdir -p ${binary_build_dir}
	builder_container=$(${docker_bin} run -id --rm --network=none --entrypoint=/bin/sh helm-builder:${IMAGE_TAG})
	${docker_bin} cp ${builder_container}:${built_binaries_dir}/helm   ${binary_build_dir}/
	${docker_bin} cp ${builder_container}:${built_binaries_dir}/tiller ${binary_build_dir}/
	${docker_bin} rm -f ${builder_container}
	${docker_bin} rmi helm-builder:${IMAGE_TAG}

	# Build tiller contaienr image
	rsync -rlpD ${binary_build_dir}/* ${docker_build_dir}/tiller/
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		--tag tiller:${IMAGE_TAG} \
		${docker_build_dir}/tiller

	mkdir -p ${docker_save_dir}
	${docker_bin} save tiller:${IMAGE_TAG} | xz -z -T2 > "${docker_save_dir}/tiller:${IMAGE_TAG}.tar"
	${docker_bin} rmi tiller:${IMAGE_TAG}
}

do_install () {
	install -d ${D}${caas_container_tar_path}
	rsync -rlpD ${docker_save_dir}/* ${D}${caas_container_tar_path}

	install -d ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/${COMPONENT} ${D}${roles_path}

	install -D ${S}/ansible/playbooks/${COMPONENT}.yaml ${D}/${playbooks_path}/${COMPONENT}.yaml
	install -D -m 0755 ${binary_build_dir}/${COMPONENT} ${D}/${bindir}/${COMPONENT}
}

pkg_postinst_${PN} () {
	mkdir -p $D${postconfig_path}
	ln -s ${playbooks_path}/${COMPONENT}.yaml $D${postconfig_path}/
}

pkg_postrm_${PN} () {
	if [ $1 -eq 0 ]; then
		rm -f ${postconfig_path}/${COMPONENT}.yaml
	fi
}


FILES_${PN} += "\
    ${bindir} \
    ${roles_path} \
    ${playbooks_path} \
"

INSANE_SKIP_${PN} += "already-stripped"
