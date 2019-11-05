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

SUMMARY = "Containers as a Service cpu-pooler component"
DESCRIPTION = "This RPM contains the cpu-pooler container image, \
  process-starter binary and related deployment artifacts for the \
  CaaS subsystem. \
"
HOMEPAGE = "https://github.com/nokia/CPU-Pooler"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c8c61a9e78acc1e713ef4c6c14e14e54"

PROTOCOL = "https"
SRCREV = "d2abe03fa20cf88bd524dc030ddb25e72bca2f2b"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-cpupooler;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "0.3.0"
MINOR_VERSION = "1"
CPUPOOLER_VERSION = "2467d8805f309c4294a0e63af70038a7d1372391"
DEPENDENCY_MANAGER_VERSION = "0.5.0"
PROCESS_STARTER_INSTALL_PATH = "/opt/bin/"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "cpupooler"
COMPONENT_PART = "process-starter"

DOCKER_EXTRA_ARG += " \
    --build-arg DEPENDENCY_MANAGER=${DEPENDENCY_MANAGER_VERSION} \
    --build-arg CPUPOOLE=${CPUPOOLER_VERSION} \
"

do_compile () {
	# build the process-starter binary inside a builder conatiner
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		--tag ${COMPONENT_PART}:builder \
		${docker_build_dir}/${COMPONENT_PART}

	# create a directory for process-starter binary
	mkdir -p ${B}/results

	# run the builder conatiner for process-starter binary
	${docker_bin} run \
		-id \
		--rm \
		--network=host \
		--privileged \
		-e HTTP_PROXY=${http_proxy} \
		-e HTTPS_PROXY=${https_proxy} \
		-e NO_PROXY=${no_proxy} \
		-e http_proxy=${http_proxy} \
		-e https_proxy=${https_proxy} \
		-e no_proxy=${no_proxy} \
		--entrypoint=/bin/sh \
		${COMPONENT_PART}:builder

	# get the process-starter binary
	${docker_bin} cp $(${docker_bin} ps | grep "${COMPONENT_PART}:builder" \
		| awk -F' ' '{ print $1 }'):/process-starter ${B}/results/
	
	# rm container
	for container_ran in $(${docker_bin} ps -a | grep "${COMPONENT_PART}:builder" \
		| awk -F' ' '{ print $1 }'); do
		${docker_bin} rm -f $container_ran
	done

	# remove docker image
	${docker_bin} rmi -f ${COMPONENT_PART}:builder


	# build the cpu pooler
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		--tag ${COMPONENT}:${IMAGE_TAG} \
		${docker_build_dir}/${COMPONENT}

	# create a save folder
	mkdir -p ${docker_save_dir}
	${docker_bin} save ${COMPONENT}:${IMAGE_TAG} | xz -z -T2 \
		> ${docker_save_dir}/${COMPONENT}:${IMAGE_TAG}.tar

	# remove docker image
	${docker_bin} rmi ${COMPONENT}:${IMAGE_TAG}
}

do_install_append () {
	install -D ${B}/results/process-starter ${D}${PROCESS_STARTER_INSTALL_PATH}/process-starter

	install -D ${S}/ansible/playbooks/${COMPONENT}.yaml ${D}/${playbooks_path}/${COMPONENT}.yaml

	install -d ${D}${roles_path}/
	rsync -rlpD ${S}/ansible/roles/${COMPONENT} ${D}${roles_path}
}

pkg_postinst_ontarget_${PN} () {
	mkdir -p ${postconfig_path}
	ln -s ${playbooks_path}/${COMPONENT}.yaml ${postconfig_path}/
}

pkg_postrm_${PN} () {
	if [ $1 -eq 0 ]; then
		rm -f ${postconfig_path}/${COMPONENT}.yaml
	fi
}


FILES_${PN} += "\
    ${PROCESS_STARTER_INSTALL_PATH} \
    ${roles_path} \
    ${playbooks_path} \
"
