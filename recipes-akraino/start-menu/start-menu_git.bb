DESCRIPTION = "This RPM contains code and support files for installing start menu"
HOMEPAGE = "https://gerrit.akraino.org/r/ta/start-menu"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "b375099e93d04ad442dc74dedba26facbaf48c62"
S = "${WORKDIR}/git/"
GIT = "${WORKDIR}/git"


SRC_URI = "git://gerrit.akraino.org/r/ta/start-menu.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit akraino-version systemd 

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "start-menu.service"


DEPENDS = "rsync-native"

do_install() {
       install -d ${D}/opt/start-menu/
       install -d ${D}/etc/userconfig/
       install -d ${D}${systemd_system_unitdir}

       rsync -av ${S}/src/* ${D}/opt/start-menu
       rsync -av ${S}/services/start-menu.service ${D}${systemd_system_unitdir}/start-menu.service
}

FILES_${PN} = " \
       /opt \
       /usr \
       /etc \
       ${systemd_system_unitdir}/ \
"

RDEPENDS_${PN} = "dialog bash"
