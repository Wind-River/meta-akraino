DESCRIPTION = "This RPM contains Access Management component for Akraino REC blueprint"
HOMEPAGE = "https://wiki.akraino.org/pages/viewpage.action?pageId=6128402"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "34040e04398054a8ab335e3b8ff5dae7ddcd3472"
S = "${WORKDIR}/git/src"

SRC_URI = "git://gerrit.akraino.org/r/ta/access-management.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit setuptools useradd akraino-version

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-r access-manager"

do_install_append() {
    install -m 0700 -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/git/systemd/auth-server.service ${D}${systemd_system_unitdir}/
    install -m 0700 -d ${D}/var/log/access_management
    install -D -m 755 ${WORKDIR}/git/secrets/am-secrets.yaml ${D}/${sysconfdir}/required-secrets/am-secrets.yaml
}

SYSTEMD_SERVICE_${PN} = "auth-server.service"

FILES_${PN} += "\
    ${systemd_system_unitdir}/auth-server.service \
"


DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} += " \
        python-flask \
        python-flask-restful \
        python-configparser \
        mod-wsgi \
        python-peewee \
        "
