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

SUMMARY = "Containers as a Service Registry component"
DESCRIPTION = "This contains the docker registry container and ansible \
  for caas subsystem. This container contains the registry service. \
"
HOMEPAGE = "https://github.com/docker/distribution"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c8c61a9e78acc1e713ef4c6c14e14e54"

PROTOCOL = "https"
SRCREV = "0b89aad6aa6de18c1cc71425fd9f20c97c5c419b"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-registry;protocol=${PROTOCOL}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "2.7.1"
MINOR_VERSION = "6"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "registry"
DOCKER_EXTRA_ARG += " \
    --build-arg REGISTRY=${MAJOR_VERSION} \
"

do_install_append () {
	install -d ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/${COMPONENT} ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/${COMPONENT}_pre_config ${D}${roles_path}

	install -d ${D}/${playbooks_path}
	rsync -rlpD ${S}/ansible/playbooks/${COMPONENT}.yaml ${D}/${playbooks_path}
	rsync -rlpD ${S}/ansible/playbooks/${COMPONENT}_pre_config.yaml ${D}/${playbooks_path}
}

pkg_postinst_ontarget_${PN} () {
	mkdir -p ${postconfig_path}
	ln -s ${playbooks_path}/${COMPONENT}.yaml ${postconfig_path}/
	ln -s ${playbooks_path}/${COMPONENT}_pre_config.yaml ${postconfig_path}/
}

pkg_postrm_${PN} () {
	if [ $1 -eq 0 ]; then
		rm -f ${postconfig_path}/${COMPONENT}.yaml
		rm -f ${postconfig_path}/${COMPONENT}_pre_config.yaml
	fi
}

FILES_${PN} += "\
    ${roles_path} \
    ${playbooks_path} \
"
