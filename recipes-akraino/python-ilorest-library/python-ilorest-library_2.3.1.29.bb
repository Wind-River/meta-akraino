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

DESCRIPTION = "HPE RESTful API for iLO is a RESTful application programming interface for the management of iLO and iLO Chassis Manager based HPE servers."
HOMEPAGE = "https://github.com/HewlettPackard/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5b4a1b52d94480d1db38911e41241227"

DEPENDS += " \
    python-pip \
    python-pbr-native \
    "

SRCREV = "f6dae68800d20af859ed958c322108604d6a998a"
SRC_URI = "https://github.com/HewlettPackard/${PN}/archive/${SRCREV}.zip \
           file://0001-initial.patch \
"
SRC_URI[md5sum] = "94c7375707ef40393ec56f12d8245fe1"
SRC_URI[sha256sum] = "f26684e1a795fabcc7028ff58b7749d76dd220420f21022dd8b8f1ca0a02bb98"
S = "${WORKDIR}/${PN}-${SRCREV}"

inherit distutils-base

do_install_append() {
    ${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN} setup.py install \
        --root ${D} --no-compile --install-purelib ${PYTHON_SITEPACKAGES_DIR} \
        --install-scripts /usr/bin build_scripts --executable ${bindir}/python
}

FILES_${PN} += "\
    ${PYTHON_SITEPACKAGES_DIR} \
"

RDEPENDS_${PN} += " \
    bash \
    python \
    "
