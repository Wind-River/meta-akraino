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

SUMMARY = "galera - Synchronous multi-master wsrep provider (replication engine)"
DESCRIPTION = " \
Galera is a fast synchronous multi-master wsrep provider (replication engine) \
for transactional databases and similar applications. For more information \
about wsrep API see http://launchpad.net/wsrep. For a description of Galera \
replication engine see http://www.codership.com. \
"
HOMEPAGE = "https://github.com/codership/galera"

LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "boost openssl libcheck"

SRC_URI = " \
    https://github.com/codership/galera/archive/release_${PV}.tar.gz;apply=no;name=galera; \
    https://github.com/codership/wsrep-API/archive/release_v25.tar.gz;apply=no;name=wsrep; \
"
SRC_URI[galera.md5sum] = "e0d5fb25bdaa0a0f68fe406e3ecec8d0"
SRC_URI[galera.sha256sum] = "c0ecf690664f2d701ad9bca644afcaaea3dec94d9ab858cbf64b40ae87d3521c"
SRC_URI[wsrep.md5sum] = "0d9943defefbd9ac13a8d246e3013dda"
SRC_URI[wsrep.sha256sum] = "fe0d0854e128c95ab8fad68e6deb9365d8520204ee6933aa4998157b3b6defef"

S = "${WORKDIR}/galera-release_${PV}"

inherit scons systemd

MAJOR_VERSION = "25"
# mv wsrep to the expected location for building
do_compile_prepend() {
    rm -rf ${S}/wsrep/src
    cp -rf ${WORKDIR}/wsrep-API-release_v${MAJOR_VERSION}/ ${S}/wsrep/src
}

# disable unit test
EXTRA_OESCONS = " \
    tests='0' \
"


do_install() {
    install -d -m 0755 ${D}/${sysconfdir}/sysconfig
    install -p -m 0644 ${S}/garb/files/garb.cnf ${D}/${sysconfdir}/sysconfig/garb.cnf
    install -d -m 0755 ${D}/${bindir}
    install -p -m 0755 ${S}/garb/files/garb-systemd ${D}/${bindir}/garb-systemd
    install -d -m 0755 ${D}/${systemd_system_unitdir}
    install -p -m 0644 ${S}/garb/files/garb.service ${D}/${systemd_system_unitdir}/garb.service
    install -d -m 0755 ${D}/${libdir}
    install -p -m 0755 ${S}/libgalera_smm.so ${D}/${libdir}/libgalera_smm.so
    install -d -m 0755 ${D}/${sbindir}
    install -p -m 0755 ${S}/garb/garbd ${D}/${sbindir}/garbd
}

# exclude .so file from default dev package
FILES_SOLIBSDEV = ""

FILES_${PN} += " \
    ${systemd_system_unitdir}/garb.service \
    ${libdir}/libgalera_smm.so \
"

RDEPENDS_${PN} += " \
    systemd \
    nmap \
    bash \
"
