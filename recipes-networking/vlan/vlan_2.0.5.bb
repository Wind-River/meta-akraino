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

SUMMARY = "ifupdown integration for vlan configuration"
HOMEPAGE = "https://salsa.debian.org/debian/vlan"
SECTION = "misc"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://debian/copyright;;md5=a99f1f04fc1d4f5d723231f8937bdc2b"

SRC_URI = "https://deb.debian.org/debian/pool/main/v/vlan/vlan_2.0.5.tar.xz"

SRC_URI[md5sum] = "e6994250748fa3ee6d99f3ac292b7eb9"
SRC_URI[sha256sum] = "ccf261839b79247be8dae93074e1c5fcbce3807787a0ff7aed4e1f7a9095c465"

do_install () {
    install -d ${D}${base_sbindir}
    install -m 0755 ${S}/vconfig ${D}${base_sbindir}/

    install -d ${D}${sysconfdir}
    cp -av --no-preserve=ownership ${S}/debian/network ${D}${sysconfdir}
}
