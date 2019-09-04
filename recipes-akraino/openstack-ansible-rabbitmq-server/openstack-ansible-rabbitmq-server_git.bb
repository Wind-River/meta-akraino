DESCRIPTION = "OpenStack-Ansible RabbitMQ server"
HOMEPAGE = "https://github.com/openstack/openstack-ansible-rabbitmq_server"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fa818a259cbed7ce8bc2a22d35a464fc"

SRCNAME = "openstack-ansible-rabbitmq_server"

SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/queens \
    file://0001-initial.patch \
    "

SRCREV = "cffd1ebd45e20331ee505568cd34c277d3225138"
PV = "17.0.2+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += " \
        rsync-native \
        "

RDEPENDS_${PN} += " \
        bash \
        "
do_install() {

        mkdir -p ${D}${sysconfdir}/ansible/roles/rabbitmq_server
        rsync -rlpD --exclude="patches" --exclude=".*" . ${D}${sysconfdir}/ansible/roles/rabbitmq_server

}

