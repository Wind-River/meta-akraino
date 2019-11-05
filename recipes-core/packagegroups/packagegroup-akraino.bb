#
# Copyright (C) 2019 Wind River Systems, Inc.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

DESCRIPTION = "Packagegroup for Akraino specific packages"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = " \
    ${PN}-base \
    ${PN}-docker \
"

RDEPENDS_${PN} = "\
    ${PN}-base \
    ${PN}-docker \
"

RDEPENDS_${PN}-base = "\
    access-management \
    ansible-role-ntp \
    caas-security \
    caas-install \
    caas-lcm \
    infra-ansible \
    cm-plugins \
    config-encoder-macros \
    config-manager \
    distributed-state-server \
    hostcli \
    hw-detector \
    image-provision \
    lockcli \
    monitoring \
    openstack-ansible \
    openstack-ansible-galera-client \
    openstack-ansible-galera-server \
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
    python-ironic \
    python-ironic-virtmedia-driver \
    python-ironicclient \
    python-peewee \
    python-yarf \
    storage \
    start-menu \
"

RDEPENDS_${PN}-docker = "\
    caas-chartrepo \
    caas-custom-metrics \
    caas-elasticsearch \
    caas-etcd \
    caas-fluentd \
    caas-metrics-server \
    caas-prometheus \
    caas-registry \
    caas-sriovdp \
    caas-cpupooler \
    caas-flannel \
    caas-kubedns \
    caas-swift \
"

# Not added for now:
#    caas-danm
#    caas-hyperdanm
#    caas-helm
#    caas-kubernetes
