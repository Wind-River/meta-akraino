DESCRIPTION = "s RPM contains source code for the config manager."
HOMEPAGE = "https://wiki.akraino.org/pages/viewpage.action?pageId=6128402"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "50946f85336ce917e6004e533a5f6aefee2c5474"
S = "${WORKDIR}/git"

SRC_URI = "git://gerrit.akraino.org/r/ta/config-manager.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit akraino-version

_python_site_packages_path = "/usr/lib64/python2.7/site-packages/"
_platform_bin_path = "/usr/sbin"

do_install_append() {
    install -m 0700 -d ${D}${systemd_system_unitdir}
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

    mkdir -p ${D}/${_python_site_packages_path}/cmframework/
    set -e
    cd cmframework/src && python setup.py install --root ${D} --no-compile --install-purelib ${_python_site_packages_path} --install-scripts ${_platform_bin_path} build_scripts --executable ${bindir}/python && cd -

    cd cmdatahandlers && python setup.py install --root ${D} --no-compile --install-purelib ${_python_site_packages_path} --install-scripts ${_platform_bin_path} build_scripts --executable ${bindir}/python && cd -

    mkdir -p ${D}/etc/service-profiles/
    cp serviceprofiles/profiles/*.profile ${D}/etc/service-profiles/

    cd serviceprofiles/python && python setup.py install --root ${D} --no-compile --install-purelib ${_python_site_packages_path} build_scripts --executable ${bindir}/python && cd -

    cd hostcli && python setup.py install --root ${D} --no-compile --install-purelib ${_python_site_packages_path} build_scripts --executable ${bindir}/python && cd -
}

SYSTEMD_SERVICE_${PN} = "config-manager.service cmagent.service"

FILES_${PN} += "\
    ${systemd_system_unitdir}/config-manager.service \
    ${systemd_system_unitdir}/cmagent.service \
    /opt/cmframework \
    ${_python_site_packages_path}\
"

pkg_postinst_${PN}() {
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
        "
