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

DESCRIPTION = "Python ServerView Common Command Interface (SCCI) Client Library"
HOMEPAGE = "https://github.com/openstack/scciclient"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

PV = "0.8.0+git${SRCPV}"
SRCREV = "d8f1f1c73bd95d21e540cb6e3acb75f5185de116"

SRCNAME = "scciclient"
SRC_URI = "git://github.com/openstack/python-${SRCNAME}.git;branch=stable/stein"

S = "${WORKDIR}/git"

inherit setuptools

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

# RDEPENDS_default: 
RDEPENDS_${PN} += " \
        python-pbr \
        python-babel \
        python-pyghmi \
        python-pysnmp \
        python-requests \
        python-oslo.utils \
        python-oslo.serialization \
       "
