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

DESCRIPTION = "This is fork of pyzeroconf, Multicast DNS Service Discovery for Python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b8eec2f0885ebe1362d0bdb1617f61b5"

SRC_URI[md5sum] = "e7b301eddd703ab74a48c59a8fda1f97"
SRC_URI[sha256sum] = "510a414b270986fba3c79cb76d90a4c910c701bfb43ff983a5d4e92846050e17"

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

PYPI_PACKAGE = "pysendfile"
inherit pypi setuptools
