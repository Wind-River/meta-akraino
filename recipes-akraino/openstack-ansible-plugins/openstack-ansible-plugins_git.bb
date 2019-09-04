DESCRIPTION = "OpenStack-Ansible plugins"
HOMEPAGE = "https://github.com/openstack/openstack-ansible-plugins"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=26046196bd9508ed02566e2f150f79f2"

SRCNAME = "openstack-ansible-plugins"

SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/queens \
    file://0001-initial.patch \
    "

SRCREV = "cecb9e78fe0b854334cf4e1e2e48abf17d8dddc9"
PV = "17.0.2+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += " \
        rsync-native \
        "

RDEPENDS_${PN} += " \
        bash \
        "
do_install() {

        mkdir -p ${D}${sysconfdir}/ansible/roles/plugins
        rsync -rlpD --exclude="patches" --exclude=".*" . ${D}${sysconfdir}/ansible/roles/plugins

}

