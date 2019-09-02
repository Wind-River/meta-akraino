DESCRIPTION = "This RPM contains code for the keepalived based monitoring"
HOMEPAGE = "https://wiki.akraino.org/pages/viewpage.action?pageId=6128402"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "6ee5a863bb9116f8af7b751fb728f8a428526f05"
S = "${WORKDIR}/git"

SRC_URI = "git://gerrit.akraino.org/r/ta/monitoring.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit akraino-version

do_install_append() {
    mkdir -p ${D}/opt/monitoring/
    cp ${S}/src/*.sh ${D}/opt/monitoring/
    cp ${S}/src/*.py ${D}/opt/monitoring/

    mkdir -p ${D}/etc/monitoring/quorum-state-changed-actions
    mkdir -p ${D}/etc/monitoring/node-state-changed-actions

    mkdir -p ${D}/etc/monitoring/active-standby-services
    cp ${S}/active-standby-services/*.service ${D}/etc/monitoring/active-standby-services/

    cp ${S}/active-standby-services/active-standby-controller.sh ${D}/etc/monitoring/node-state-changed-actions/
    cp ${S}/active-standby-services/active-standby-monitor.sh ${D}/opt/monitoring/
}

FILES_${PN} += "\
    /opt/monitoring/* \
    /etc/monitoring/  \
"

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} += " \
        bash \
        python \
        "
