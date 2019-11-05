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

DESCRIPTION = "HTTPS client implementation for httplib and urllib2"
HOMEPAGE = "https://github.com/cedadev/ndg_httpsclient"
SECTION = "devel/python"
LICENSE = "STFC"
LIC_FILES_CHKSUM = "file://ndg/httpsclient/LICENSE;md5=f6f6af2edb690eaeee9a16558cafbada"

SRC_URI[md5sum] = "bf962f06bddb9ad84afabcf2dfe893b5"
SRC_URI[sha256sum] = "25235c2f67cd97ba385d0103076fa7e0f322a6cb01e490e7d296279243126c55"

SRCNAME = "ndg_httpsclient"

SRC_URI[md5sum] = "58071f6aa0981117a35157859e4a678f"
SRC_URI[sha256sum] = "0d99da3226ff7ca89cc1c953d5af00e2d045e34abe074b20020f2492c8ab8477"

SRC_URI = "git://github.com/cedadev/${SRCNAME}.git; \
    "

SRCREV = "af9fed2c22e283c745135a8d745e36b8540bb8cc"
PV = "0.5.1+git${SRCPV}"
S = "${WORKDIR}/git"


inherit setuptools
DEPENDS_${PN} += " \
    python-pip \
    python-native \
    "

RDEPENDS_${PN} += " \
    python-pyopenssl \
    python-pyasn1 \
    "
