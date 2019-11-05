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

DESCRIPTION = "Host network configuration tool"
HOMEPAGE = "http://pypi.python.org/pypi/os-net-config"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=34400b68072d710fecd0a2940a0d1658"

SRC_URI = "https://files.pythonhosted.org/packages/26/8d/c9ce44fd6a805a4ec56cba59c0a6743fba2230b7b79aa57a48709e94f47c/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "f61752ecd62fc981a6181c95f8940936"
SRC_URI[sha256sum] = "09455742f5f02b77c82c20d534fcbbe32a49a5f0af64b1cabe736e79151cf2f9"

inherit setuptools

do_install_append() {
}

FILES_${PN} += "\
"

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} += " \
        python-anyjson \
        python-eventlet \
        python-oslo.concurrency \
        python-oslo.config \
        python-oslo.utils \
        python-netaddr \
        python-netifaces \
        python-iso8601 \
        python-six \
        initscripts \
        ethtool \
        openvswitch \
        python-pyyaml \
        python-jsonschema \
        dhcp-client \
        driverctl \
        "

