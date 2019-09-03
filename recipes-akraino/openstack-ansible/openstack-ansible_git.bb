DESCRIPTION = "OpenStack-Ansible provides Ansible playbooks and roles for the deployment and configuration of an OpenStack environment."
HOMEPAGE = "https://opendev.org/openstack/openstack-ansible"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d2794c0df5b907fdace235a619d80314"

SRCNAME = "openstack-ansible"

SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/queens \
    file://0001-initial.patch \
    "

SRC_URI[sha256sum] = "c0bd46b327a9b64dd3282f0eb82661a99c13da615b21fe981e65ac00098db385"

SRCREV = "2bdc44499ba81d3a519bb23fceca3096df62f27c"
PV = "17.0.2+git${SRCPV}"
S = "${WORKDIR}/git"

inherit setuptools

DEPENDS += " \
        python-pip \
        python-pbr-native \
        rsync-native \
        "
RDEPENDS_${PN} += " \
        bash \
        python-pyasn1 \
        python-pyopenssl \
        python-ndg-httpsclient \ 
        python-netaddr \ 
        python-prettytable \
        python-memcached \
        python-pyyaml \
        python-virtualenv \
        "

FILES_${PN} += "/opt/openstack-ansible/* /usr/local/bin/*" 
do_install_append() {

        install -d  ${D}/opt/openstack-ansible
        rsync -rlpD --exclude="inventory/env.d/" --exclude=".*" . ${D}/opt/openstack-ansible/
        install -d ${D}/usr/local/bin/
        install -m 0755 scripts/openstack-ansible ${D}/usr/local/bin/
        install -m 0644 scripts/openstack-ansible.rc ${D}/usr/local/bin/
        install -m 0755 scripts/setup-controller.sh ${D}/usr/local/bin/
        # Create a dummy directory for role pip_install,apt_package_pinning are dependencies for some roles.
        # They are anyway masked with --skip-tags option while running ansible.
        mkdir -p ${D}${sysconfdir}/ansible/roles/etcd
        mkdir -p ${D}${sysconfdir}/ansible/roles/bird
        mkdir -p ${D}${sysconfdir}/ansible/roles/pip_install
        mkdir -p ${D}${sysconfdir}/ansible/roles/apt_package_pinning
        mkdir -p ${D}${bootstrapping_path}
        mkdir -p ${D}${provisioning_path}
        mkdir -p ${D}${postconfig_path}

}

pkg_postinst_${PN}() {
        ln -s /opt/openstack-ansible/playbooks/galera-install.yml ${bootstrapping_path}
        ln -s /opt/openstack-ansible/playbooks/rabbitmq-install.yml ${bootstrapping_path}
        ln -s /opt/openstack-ansible/playbooks/rsyslog-install.yml ${bootstrapping_path}
        ln -s /opt/openstack-ansible/playbooks/os-ironic-install.yml ${bootstrapping_path}
        
        ln -s /opt/openstack-ansible/playbooks/ntp-config.yml ${bootstrapping_path}
        ln -s /opt/openstack-ansible/playbooks/ntp-config.yml ${postconfig_path}
        
        #postconfig
        ln -s /opt/openstack-ansible/playbooks/galera-install.yml ${postconfig_path}
        ln -s /opt/openstack-ansible/playbooks/memcached-install.yml ${postconfig_path}
        ln -s /opt/openstack-ansible/playbooks/rabbitmq-install.yml ${postconfig_path}
        ln -s /opt/openstack-ansible/playbooks/rsyslog-install.yml ${postconfig_path}
        ln -s /opt/openstack-ansible/playbooks/haproxy-install.yml ${postconfig_path}
        ln -s /opt/openstack-ansible/playbooks/hosts_config.yml ${postconfig_path}
        ln -s /opt/openstack-ansible/playbooks/os-ironic-install.yml ${postconfig_path}
        ln -s /opt/openstack-ansible/playbooks/os-keystone-install.yml ${postconfig_path}
        
        mkdir -p ${sysconfdir}/required-secrets
        cp -f /opt/openstack-ansible/etc/openstack_deploy/user_secrets.yml ${sysconfdir}/required-secrets/000-user_secrets.yml

}
