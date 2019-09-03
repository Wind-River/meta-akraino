DESCRIPTION = "This Ansible role creates the configuration files used by various OpenStack CLI tools."
HOMEPAGE = "https://github.com/openstack/openstack-ansible-openstack_openrc"
SECTION = "devel/ansible"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d2794c0df5b907fdace235a619d80314"

SRCNAME = "openstack-ansible-openstack_openrc"

SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/queens \
    file://0001-initial.patch \
    "

SRCREV = "e86c73ef9af547b30a4aab0d39aca96359bf5ce4"
PV = "17.0.2+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += " \
        rsync-native \
        "

RDEPENDS_${PN} += " \
        bash \
        "
do_install() {

        mkdir -p ${D}${sysconfdir}/ansible/roles/openstack_openrc
        rsync -rlpD --exclude="patches" --exclude=".*" . ${D}${sysconfdir}/ansible/roles/openstack_openrc

}

