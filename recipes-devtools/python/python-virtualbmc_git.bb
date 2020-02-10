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

DESCRIPTION = "A virtual BMC for controlling virtual machines using IPMI commands."
HOMEPAGE = "https://github.com/openstack/virtualbmc"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"


SRC_URI = "git://github.com/openstack/virtualbmc.git;"

SRCREV = "88588769a47ce1c0836bfdcb26729a263c30f8d9"
PV = "1.2.0+git${SRCPV}"
S = "${WORKDIR}/git"


inherit setuptools

DEPENDS += " \
    python-pbr-native \	
    "

RDEPENDS_${PN} += " \
    python-six \
    libvirt-python \
    python-pyghmi \
    python-prettytable \
    python-pbr \
    "
