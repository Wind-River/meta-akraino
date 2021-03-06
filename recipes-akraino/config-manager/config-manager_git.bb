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

DESCRIPTION = "s RPM contains source code for the config manager."
HOMEPAGE = "https://wiki.akraino.org/pages/viewpage.action?pageId=6128402"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "6304ee33ecdd870a39781b9adb273d6b8f4f7802"
S = "${WORKDIR}/git"

SRC_URI = "git://gerrit.akraino.org/r/ta/config-manager.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH} \
           file://0001-cmdatahandlers-work-around.patch \
"

inherit akraino-version distutils-base

_platform_bin_path = "/usr/local/bin"
native_python_bin = "${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN}"

do_install_append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/cmframework/systemd/config-manager.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${S}/cmframework/systemd/cmagent.service ${D}${systemd_system_unitdir}/

    mkdir -p ${D}/opt/cmframework/activators
    mkdir -p ${D}/opt/cmframework/validators
    mkdir -p ${D}/opt/cmframework/userconfighandlers
    mkdir -p ${D}/opt/cmframework/inventoryhandlers

    # mkdir -p ${D}/opt/cmframework/scripts
    install -d ${D}/opt/cmframework/scripts
    cp cmframework/scripts/*.sh ${D}/opt/cmframework/scripts
    install -D -m 0755 ${S}/cmframework/scripts/cmserver ${D}/opt/cmframework/scripts
    install -D -m 0755 ${S}/cmframework/scripts/cmagent ${D}/opt/cmframework/scripts
    install -D -m 0664 ${S}/cmframework/scripts/redis.conf ${D}/opt/cmframework/scripts
    
    install -d ${D}/etc/cmframework/masks.d
    install -D -m 0664 ${S}/cmframework/config/masks.d/default.cfg ${D}/etc/cmframework/masks.d/

    mkdir -p ${D}/${PYTHON_SITEPACKAGES_DIR}/cmframework/
    set -e
    cd cmframework/src && ${native_python_bin} setup.py install --root ${D} --no-compile --install-purelib ${PYTHON_SITEPACKAGES_DIR} --install-scripts ${_platform_bin_path} build_scripts --executable ${bindir}/python && cd -

    cd cmdatahandlers && ${native_python_bin} setup.py install --root ${D} --no-compile --install-purelib ${PYTHON_SITEPACKAGES_DIR} --install-scripts ${_platform_bin_path} build_scripts --executable ${bindir}/python && cd -

    mkdir -p ${D}/etc/service-profiles/
    cp serviceprofiles/profiles/*.profile ${D}/etc/service-profiles/

    cd serviceprofiles/python && ${native_python_bin} setup.py install --root ${D} --no-compile --install-purelib ${PYTHON_SITEPACKAGES_DIR} build_scripts --executable ${bindir}/python && cd -

    cd hostcli && ${native_python_bin} setup.py install --root ${D} --no-compile --install-purelib ${PYTHON_SITEPACKAGES_DIR} build_scripts --executable ${bindir}/python && cd -

    # fix hardcoded paths
    sed -i -e "s;${base_bindir}/redis;${bindir}/redis;" ${D}/opt/cmframework/scripts/common.sh
}

SYSTEMD_SERVICE_${PN} = "config-manager.service cmagent.service"

FILES_${PN} += "\
    ${systemd_system_unitdir}/config-manager.service \
    ${systemd_system_unitdir}/cmagent.service \
    /opt/cmframework \
    ${_platform_bin_path} \
    ${PYTHON_SITEPACKAGES_DIR}\
"

pkg_postinst_ontarget_${PN}() {
    ln -sf /opt/cmframework/scripts/inventory.sh /opt/openstack-ansible/inventory/
    chmod -x /usr/lib/systemd/system/config-manager.service
    chmod -x /usr/lib/systemd/system/cmagent.service
}

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} += " \
        bash \
        python \
        python-ipaddr \
        "
