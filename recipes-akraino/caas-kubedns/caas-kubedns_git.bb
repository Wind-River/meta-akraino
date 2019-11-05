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

SUMMARY = "Containers as a Service Kube DNS component"
DESCRIPTION = "This contains the kubedns container image, \
  and related deployment artifacts for the CaaS subsystem. \
"
HOMEPAGE = "https://github.com/kubernetes/dns"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ff253ad767462c46be284da12dda33e8"

PROTOCOL = "https"
SRCREV = "b829734396262b0158289449d812894cb3624707"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-kubedns;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "1.15.4"
MINOR_VERSION = "2"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "kubedns"
DOCKER_EXTRA_ARG += "--build-arg KUBEDNS=${MAJOR_VERSION}"

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
