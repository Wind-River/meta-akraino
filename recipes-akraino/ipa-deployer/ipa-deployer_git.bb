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

SUMMARY = "Deployment image for ironic python agent"
DESCRIPTION = "Deployment image for ironic python agent"
HOMEPAGE = "https://gerrit.akraino.org/r/ta/ipa-deployer"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PROTOCOL = "https"
SRCREV_ipa-deployer = "a45f45480df7dc03311da9a4f0f3e63ab2863217"

SRC_URI = "git://gerrit.akraino.org/r/ta/ipa-deployer;protocol=${PROTOCOL};name=ipa-deployer \
           https://nexus3.akraino.org/repository/rpm.snapshots/TA/release-1/rpms/x86_64/ipa-deployer-c6.ga45f454-1.el7.centos.ta.noarch.rpm;name=rpm-ipa-deployer;subdir=rpm-ipa-deployer \
"

SRC_URI[rpm-ipa-deployer.md5sum] = "d91d86a86ff70f3af61cbf54154b1f19"
SRC_URI[rpm-ipa-deployer.sha256sum] = "e368bdc874113f2f5b4429c399ef177892598bf400d35fa31c6fc4f4595a329e"

S = "${WORKDIR}/git"

inherit akraino-version

do_compile () {
	:
}

do_install () {
	install -d ${D}/opt/images
	install ${WORKDIR}/rpm-ipa-deployer/opt/images/ironic-deploy.iso ${D}/opt/images
}

FILES_${PN} += "/opt/images"
