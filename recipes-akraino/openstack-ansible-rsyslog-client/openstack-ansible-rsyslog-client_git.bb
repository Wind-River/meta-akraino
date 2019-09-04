DESCRIPTION = "OpenStack rsyslog client"
HOMEPAGE = "https://github.com/openstack/openstack-ansible-rsyslog_client"
SECTION = "devel/ansible"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fa818a259cbed7ce8bc2a22d35a464fc"

SRCNAME = "openstack-ansible-rsyslog_client"

SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/queens \
    file://0001-initial.patch \
    "

SRCREV = "ed8e178c38a28cab87b8d9bd4396caccf8c0e790"
PV = "17.0.2+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += " \
        rsync-native \
        "

RDEPENDS_${PN} += " \
        bash \
        "
do_install() {

        mkdir -p ${D}${sysconfdir}/ansible/roles/rsyslog_client
        rsync -rlpD --exclude="patches" --exclude=".*" . ${D}${sysconfdir}/ansible/roles/rsyslog_client

}

