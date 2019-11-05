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

DESCRIPTION = "Yet Another Restfulframework."
HOMEPAGE = "https://gerrit.akraino.org/r/ta/yarf"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "51e80b41a9ba507b2e877f93ea3037e92ee3f78e"
S = "${WORKDIR}/git/src"
GIT = "${WORKDIR}/git"

SRCNAME = "yarf"

SRC_URI = "git://gerrit.akraino.org/r/ta/yarf.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit setuptools akraino-version systemd useradd

USERADD_PACKAGES = "${SRCNAME}"
USERADD_PARAM_${SRCNAME}  = "-r -s /sbin/nologin -M restapi"

PACKAGES =+ " ${SRCNAME}" 

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_PACKAGES = "${SRCNAME}"
SYSTEMD_SERVICE_${SRCNAME} = "restapi.service"

FILES_${SRCNAME} += " \
                  ${bindir}/* \
                  ${sysconfdir}/* \
                  ${systemd_unitdir}/system/restapi.service \
                   "

RDEPENDS_${PN} += " \
        mod-wsgi \
        python-flask \
        python-flask-restful \
        python-requests \
        python-six \
        "
do_install_append() {
       install -d ${D}${sysconfdir}/restful
       install -m 655 ${GIT}/conf/config.ini ${D}${sysconfdir}/restful
        
       install -d ${D}${sysconfdir}/required-secrets
       install -m 655 ${GIT}/required-secrets/restful.yaml ${D}${sysconfdir}/required-secrets

       install -d ${D}${systemd_unitdir}/system
       install -m 644 ${GIT}/systemd/restapi.service  ${D}${systemd_unitdir}/system

}

