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

DESCRIPTION = "This RPM contains code for the keepalived based monitoring"
HOMEPAGE = "https://wiki.akraino.org/pages/viewpage.action?pageId=6128402"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "6ee5a863bb9116f8af7b751fb728f8a428526f05"
S = "${WORKDIR}/git"

SRC_URI = "git://gerrit.akraino.org/r/ta/monitoring.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit akraino-version

do_install_append() {
    mkdir -p ${D}/opt/monitoring/
    cp ${S}/src/*.sh ${D}/opt/monitoring/
    cp ${S}/src/*.py ${D}/opt/monitoring/

    mkdir -p ${D}/etc/monitoring/quorum-state-changed-actions
    mkdir -p ${D}/etc/monitoring/node-state-changed-actions

    mkdir -p ${D}/etc/monitoring/active-standby-services
    cp ${S}/active-standby-services/*.service ${D}/etc/monitoring/active-standby-services/

    cp ${S}/active-standby-services/active-standby-controller.sh ${D}/etc/monitoring/node-state-changed-actions/
    cp ${S}/active-standby-services/active-standby-monitor.sh ${D}/opt/monitoring/
}

FILES_${PN} += "\
    /opt/monitoring/* \
    /etc/monitoring/  \
"

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} += " \
        bash \
        python \
        "
