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

DESCRIPTION = "A common library to be used exclusively by projects under the Ironic governance."
HOMEPAGE = "https://github.com/openstack/ironic-lib"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d2794c0df5b907fdace235a619d80314"

PV = "2.16.3+git${SRCPV}"
SRCREV = "eca4ac942a13f87d8ca836e0487595a52d872390"

SRCNAME = "ironic-lib"
SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/stein"

S = "${WORKDIR}/git"

inherit setuptools

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

# RDEPENDS_default: 
RDEPENDS_${PN} += " \
        python-pbr \
        python-oslo.concurrency \
        python-oslo.config \
        python-oslo.i18n \
        python-oslo.serialization \
        python-oslo.service \
        python-oslo.utils \
        python-oslo.log \
        python-six \
        python-zeroconf \
        python-requests \
       "
