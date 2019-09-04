DESCRIPTION = "OpenStack-Ansible role for Bare Metal (ironic) service"
HOMEPAGE = "https://github.com/openstack/openstack-ansible-os_ironic"
SECTION = "devel/ansible"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fa818a259cbed7ce8bc2a22d35a464fc"

SRCNAME = "openstack-ansible-os_ironic"

SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/queens \
    file://0001-initial.patch \
    "

SRCREV = "8c33498070489e2ff645cc1286df535e2b16726b"
PV = "17.0.2+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += " \
        rsync-native \
        "

RDEPENDS_${PN} += " \
        bash \
        "
do_install() {

        mkdir -p ${D}${sysconfdir}/ansible/roles/os_ironic
        rsync -rlpD --exclude="patches" --exclude=".*" . ${D}${sysconfdir}/ansible/roles/os_ironic

}

