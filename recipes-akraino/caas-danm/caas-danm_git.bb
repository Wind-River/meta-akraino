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

SUMMARY = "Containers as a Service danm component"
DESCRIPTION = "This contains the DANM and related CNI binaries for CaaS subsystem."
HOMEPAGE = "https://github.com/nokia/danm"

LICENSE = "Apache-2.0 & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93 \
                    file://danm/LICENSE;md5=eed9a83a00903ca2207bc3aeae6e9a43"

PROTOCOL = "https"
SRCREV_caas-danm = "a7fdbdf37816d2beced1bbe1cd6f2b24c207980f"
SRCREV_danm = "${DANM_VERSION}"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-danm;protocol=${PROTOCOL};name=caas-danm \
           git://github.com/nokia/danm.git;protocol=${PROTOCOL};name=danm;destsuffix=git/danm \
           https://github.com/containernetworking/plugins/releases/download/v${CNI_VERSION}/cni-plugins-amd64-v${CNI_VERSION}.tgz;unpack=0;name=cni \
"

SRC_URI[cni.md5sum] = "802f269be88a35a173d7486a67341bb1"
SRC_URI[cni.sha256sum] = "c276f5f39f31cc9fa83ce69bebcbea5876778ccc081786d45934a59ef015c5c5"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "4.0.0"
MINOR_VERSION = "7"
DANM_VERSION = "7b0634aa1693c1d91ef5cfb025f2deda77941155"
CNI_VERSION = "0.7.0"
GO_VERSION = "1.12.1"
SRIOV_VERSION = "9e4c973b2ac517c64867e33d61aee152d70dc330"

binary_build_dir = "${B}/binary-save"
built_binaries_dir = "/binary-save"
danm_components = "danm fakeipam"
cnis = "flannel sriov"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "danm"
DANM_EXTRA_ARG = ' \
    --build-arg DANM_VERSION=${DANM_VERSION} \
    --build-arg go_version=${GO_VERSION} \
    --build-arg binaries=${built_binaries_dir} \
    --build-arg components="${danm_components}" \
'

CNI_EXTRA_ARG = " \
    --build-arg go_version=${GO_VERSION} \
    --build-arg SRIOV_VERSION=${SRIOV_VERSION} \
    --build-arg binaries=${built_binaries_dir} \
"

do_compile () {
	mkdir -p ${binary_build_dir}/cni
	tar zxf ${WORKDIR}/cni-plugins-amd64-v${CNI_VERSION}.tgz --strip-component=1 -C ${binary_build_dir}/cni 

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

	# Build CNI binaries
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		${CNI_EXTRA_ARG} \
		--tag cni-builder:${IMAGE_TAG} \
		${docker_build_dir}/cni-builder

	builder_container=$(${docker_bin} run -id --rm --network=none --entrypoint=/bin/sh cni-builder:${IMAGE_TAG})
	mkdir -p ${binary_build_dir}/built_cnis
	for cni in ${cnis}; do
		${docker_bin} cp ${builder_container}:${built_binaries_dir}/${cni} ${binary_build_dir}/built_cnis/
	done
	${docker_bin} rm -f ${builder_container}
	${docker_bin} rmi cni-builder:${IMAGE_TAG}
}

do_install () {
	mkdir -p ${D}${sysconfdir}/cni/net.d/
	rsync -rlpD ${S}/cni-config/00-danm.conf ${D}${sysconfdir}/cni/net.d/00-danm.conf
	rsync -rlpD ${S}/cni-config/flannel.conf ${D}${sysconfdir}/cni/net.d/flannel.conf

	mkdir -p ${D}/opt/cni/bin/
	# Generic CNI plugins
	# Don't use the standard ipvlan binary \
	# Don't use portmap, quick fix for CVE-2019-9946 \
	rsync -rlpD \
	      --chmod=go+rx,u+rwx \
	      --exclude=ipvlan \
	      --exclude=portmap \
	       ${binary_build_dir}/cni/* ${D}/opt/cni/bin

	# DANM
	for component in ${danm_components}; do
		install -D -m 0755 ${binary_build_dir}/danm/${component} ${D}/opt/cni/bin/${component}
	done

	# Other CNIs
	for cni in ${cnis}; do
		install -D -m 0755 ${binary_build_dir}/built_cnis/${cni} ${D}/opt/cni/bin/${cni}
	done

	# DANM CRDs
	mkdir -p ${D}/${caas_danm_crd_path}
	rsync -rlpD ${S}/danm/integration/crds/production/ ${D}/${caas_danm_crd_path}
}

FILES_${PN} += "\
    ${sysconfdir} \
    ${caas_danm_crd_path} \
    /opt/cni/bin \
"
