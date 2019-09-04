DESCRIPTION = "OpenStack-Ansible keystone"
HOMEPAGE = "https://github.com/openstack/openstack-ansible-os_keystone"
SECTION = "devel/ansible"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fa818a259cbed7ce8bc2a22d35a464fc"

SRCNAME = "openstack-ansible-os_keystone"

SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/queens \
    file://0001-initial.patch \
    "

SRCREV = "705f58fb0b3bd3599e3265a982d7d6235a2d2d8d"
PV = "17.0.2+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += " \
        rsync-native \
        "

RDEPENDS_${PN} += " \
        bash \
        "
do_install() {

        mkdir -p ${D}${sysconfdir}/ansible/roles/os_keystone
        rsync -rlpD --exclude="patches" --exclude=".*" . ${D}${sysconfdir}/ansible/roles/os_keystone

}

