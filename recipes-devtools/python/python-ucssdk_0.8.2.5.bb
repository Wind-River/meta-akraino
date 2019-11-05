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
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b0a8ffe0d25e797b355d0f5de9e61baa"

SRC_URI[md5sum] = "48fc50ada1ceb558ff460d6e5f7520ab"
SRC_URI[sha256sum] = "557f1f1a8c7a147da19d7004f10cc778a02ee48ab6f4e50b2a83ed49b25706c5"

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} = "python bash"

PYPI_PACKAGE = "UcsSdk"

inherit pypi setuptools

