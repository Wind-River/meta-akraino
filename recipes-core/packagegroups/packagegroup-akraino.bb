DESCRIPTION = "Packagegroup for Akraino specific packages"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_${PN} = "\
    access-management \
    ansible-role-ntp \
    caas-elasticsearch \
    caas-etcd \
    caas-fluentd \
    caas-install \
    caas-kubedns \
    caas-metrics-server \
    caas-prometheus \
    cm-plugins \
    config-encoder-macros \
    config-manager \
    distributed-state-server \
    hostcli \
    hw-detector \
    image-provision \
    lockcli \
    monitoring \
    openstack-ansible-galera-client \
    openstack-ansible-galera-server \
    openstack-ansible \
    openstack-ansible-haproxy-server \
    openstack-ansible-memcached-server \
    openstack-ansible-openstack-openrc \
    openstack-ansible-os-ironic \
    openstack-ansible-os-keystone \
    openstack-ansible-plugins \
    openstack-ansible-rabbitmq-server \
    openstack-ansible-rsyslog-client \
    os-net-config \
    python-ilorest-library \
    python-ironicclient \
    python-ironic \
    python-ironic-virtmedia-driver \
    python-peewee \
    python-yarf \
    storage \
"
