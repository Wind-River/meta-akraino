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

DESCRIPTION = "a set of utility libraries for interfacing and managing various components (like iLO, HPSSA) for HP Proliant Servers"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6c4db32a2fa8717faffa1d4f10136f47"

SRC_URI[sha256sum] = "ba5425fd12203e9446d14812242c65a8417dd7685c1ee7a2f78a7ed521481e3d"

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

PYPI_PACKAGE = "proliantutils"
inherit pypi setuptools
