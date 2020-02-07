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

DESCRIPTION = "shade is a simple client library for interacting with OpenStack clouds."
HOMEPAGE = "https://github.com/openstack/shade/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=34400b68072d710fecd0a2940a0d1658"

DEPENDS += " \
    python-pip \
    python-pbr-native \
"

SRC_URI = "git://github.com/openstack/shade.git;branch=stable/queens"

PV = "1.27.2+git${SRCPV}"
SRCREV = "2fe63a4d74dcff34f7a0dc90ec65f3e0cc0bf596"
S = "${WORKDIR}/git"

inherit setuptools

RDEPENDS_${PN} += " \
    bash \
    python-openstacksdk \
    python-os-client-config \
    python-pbr \
"
