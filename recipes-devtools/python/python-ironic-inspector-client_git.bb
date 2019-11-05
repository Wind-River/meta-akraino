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
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PV = "3.5.0+git${SRCPV}"
SRCREV = "9bb11505d79c97e28b32c821dda0e47ef2ef1dc7"

SRCNAME = "ironic-inspector-client"
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
        python-keystoneauth1 \
        python-oslo.i18n \
        python-pyyaml \
        python-osc-lib \
        python-requests \
        python-oslo.utils \
       "
