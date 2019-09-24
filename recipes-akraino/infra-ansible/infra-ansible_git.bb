DESCRIPTION = "This RPM contains Contains ansible playbook and roles for Akraino rec blueprint"
HOMEPAGE = "https://gerrit.akraino.org/r/ta/infra-ansible"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "a555e0e1024de52b97bbbc6c9f43e377315cda37"
S = "${WORKDIR}/git/"

SRC_URI = "git://gerrit.akraino.org/r/ta/${PN}.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit akraino-version systemd 

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "finalize-bootstrap.service sriov.service report-installation-success.service"


do_install() {
    
    mkdir -p ${D}/root/dev_tools
    cp dev_tools/* ${D}/root/dev_tools

    mkdir -p ${D}${playbooks_path}
    cp playbooks/* ${D}${playbooks_path}

    # mkdir -p ${D}%{_inventory_path}
    # cp -rf inventory/* ${D}%{_inventory_path}

    mkdir -p ${D}${roles_path}
    cp -rf roles/* ${D}${roles_path}

    mkdir -p ${D}/${finalize_path}
    ln -sf ${playbooks_path}/removevips.yml                     ${D}/${finalize_path}
    ln -sf ${playbooks_path}/monitoring.yml                     ${D}/${finalize_path}
    ln -sf ${playbooks_path}/redissync.yml                      ${D}/${finalize_path}
    ln -sf ${playbooks_path}/ansiblesync.yml                    ${D}/${finalize_path}
    ln -sf ${playbooks_path}/redisconfig.yml                    ${D}/${finalize_path}
    ln -sf ${playbooks_path}/cmserverconfig.yml                 ${D}/${finalize_path}

    mkdir -p ${D}/${secrets_path}
    cp secrets/* ${D}/${secrets_path}

    # Create links for the bootstrapping phase
    mkdir -p ${D}/${bootstrapping_path}
    ln -sf ${playbooks_path}/initial_poweroff_hosts.yml         ${D}/${bootstrapping_path}
    ln -sf ${playbooks_path}/partfs_rootdisk_inst_cont.yml      ${D}/${bootstrapping_path}
    ln -sf ${playbooks_path}/ntp-config.yml                     ${D}/${bootstrapping_path}

    # Create links for the provisioning phase
    mkdir -p ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/baremetal-install.yml              ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/partfs_rootdisk.yml                ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/allocate-cpu-cores.yml             ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/sriov_nodes.yaml                   ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/sriovdp_config.yaml                ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/performance-kernel-cmdline-set.yml ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/performance_nodes.yaml             ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/baremetal-interface-config.yml     ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/ntp-config.yml                     ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/rpm-database.yml                   ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/single_node_storage.yml            ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/ceph-deploy.yml                    ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/baremetal-interface-config.yml     ${D}/${provisioning_path}
    ln -sf ${playbooks_path}/ntp-check.yml                      ${D}/${provisioning_path}

    # Create links for the postconfig phase
    mkdir -p ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/synchonize_ssh_keys.yml                     ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/distributed-state-server-file-plugin.yml    ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/distributed-state-server-etcd-plugin.yml    ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/distributed-state-server.yml                ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/etcdansible.yml                             ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/core-handling.yml                           ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/dbwatchdog.yml                              ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/access-management.yml                       ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/accounts.yml                                ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/adminuserfile.yml                           ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/ansibleldconfig.yml                         ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/bare_lvm_backend.yml                        ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/baremetal-interface-config-post.yml         ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/certificate_update.yml                      ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/core-handling.yml                           ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/create-bash-command-auth-conf.yml           ${D}/${postconfig_path}
    # keepalive ln -sf ${playbooks_path}/dbwatchdog.yml                  ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/disable-old-node-rsyslog.yml                ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/enablecmagent.yml                           ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/haproxy-install.yml                         ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/ipv6-config.yml                             ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/kernel-core-handling.yml                    ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/motd.yml                                    ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/hostcli.yml                                 ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/oom.yml                                     ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/openssh_server_conf_hardening.yml           ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/openstack-ansible-log-dir.yml               ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/operation_system_hardening.yml              ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/restful.yml                                 ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/root-openstack-admin-credentials.yml        ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/service-profiles.yml                        ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/setup_audit.yml                             ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/setup_in_host_traffic_filtering.yml         ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/synchonize_ssh_keys.yml                     ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/systemd_services.yml                        ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/userskeyssync.yml                           ${D}/${postconfig_path}
    ln -sf ${playbooks_path}/baremetal-interface-config-post.yml         ${D}/${postconfig_path}

    ln -sf ${roles_path}/baremetal_interface_config/templates/os_net_config.j2 ${D}${roles_path}/bootstrap-host/templates/os_net_config.j2
    ln -sf /opt/config-encoder-macros ${D}${roles_path}/access-management/templates/encoder

    mkdir -p ${D}${systemd_system_unitdir}
    cp systemd/finalize-bootstrap.service ${D}${systemd_system_unitdir}
    cp systemd/sriov.service ${D}${systemd_system_unitdir}
    cp systemd/report-installation-success.service ${D}${systemd_system_unitdir}

    mkdir -p ${D}/opt/ansible-change_kernel_cmdline/
    cp systemd/finalize-bootstrap.sh ${D}/opt/ansible-change_kernel_cmdline/

    mkdir -p ${D}/opt/sriov
    cp systemd/sriov.sh ${D}/opt/sriov
}

pkg_postinst_ontarget_${PN} () {
    for role in /usr/share/ceph-ansible/roles/*; do
      ln -sf $role /etc/ansible/roles/
    done
}

FILES_${PN} = "       \
    /opt              \
    /etc              \
    /root/dev_tools   \
    ${playbooks_path} \
    ${systemd_system_unitdir} \ 
"

RDEPENDS_${PN} = "bash python"
