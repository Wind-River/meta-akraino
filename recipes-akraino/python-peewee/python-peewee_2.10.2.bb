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

DESCRIPTION = " A small, expressive ORM written in python"
HOMEPAGE = "http://github.com/coleifer/peewee/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a68e3ec7a6e09a43370f0a3fbc48660"


SRC_URI = "https://ftp.unpad.ac.id/pypi/web/packages/7a/bc/aafce76cae9362dccf70e35c16a6cc11d114ebb640bbb86d76255be5c0d6/peewee-${PV}.tar.gz"
SRC_URI[md5sum] = "d0639ecbc08f9e6a1115fc661d59a34d"
SRC_URI[sha256sum] = "2342067f48a779e35956a44cd547df883dda35153daa9fe994d970585aaec281"

S="${WORKDIR}/peewee-${PV}"

inherit setuptools 


do_install_append() {
    rm ${D}/${libdir}/python2.7/site-packages/pwiz.*
    rm ${D}/${bindir}/pwiz.py
}

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} += " \
        python-simplejson \
        "
