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

SUMMARY = "Containers as a Service flannel component"
DESCRIPTION = "This contains the Flannel container image, \
  and related deployment artifacts for the CaaS subsystem. \
"
HOMEPAGE = "https://github.com/coreos/flannel"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

PROTOCOL = "https"
SRCREV = "a7fdbdf37816d2beced1bbe1cd6f2b24c207980f"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-danm;protocol=${PROTOCOL}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "0.11.0"
MINOR_VERSION = "5"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "flannel"
DOCKER_EXTRA_ARG += " \
    --build-arg FLANNEL=${MAJOR_VERSION} \
    --build-arg FLANNEL_ARCHITECTURE=amd64 \
"

do_install_append () {
	install -d ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/${COMPONENT} ${D}${roles_path}

	install -D ${S}/ansible/playbooks/${COMPONENT}.yaml ${D}/${playbooks_path}/${COMPONENT}.yaml
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
    ${roles_path} \
    ${playbooks_path} \
"
