SUMMARY = "Cloud instance init scripts"
DESCRIPTION = "\
Cloud-init is a set of init scripts for cloud instances.  Cloud instances \
need special scripts to run during initialization to retrieve and install \
ssh keys and to let the user run various scripts. \
"
HOMEPAGE = "https://launchpad.net/cloud-init"
SECTION = "devel/python"
LICENSE = "GPLv3 & Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=c6dd79b6ec2130a3364f6fa9d6380408 \
    file://LICENSE-Apache2.0;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://LICENSE-GPLv3;md5=d32239bcb673463ab874e80d47fae504 \
"

DEPENDS = "\
    python-certifi-native \
    python-chardet-native \
    python-idna-native \
    python-pyyaml-native \
    python-requests-native \
    python-six-native \
    python-urllib3-native \
"

SRC_URI = " \
    https://launchpad.net/cloud-init/trunk/${PV}/+download/${BPN}-${PV}.tar.gz \
    file://cloud-init-source-local-lsb-functions.patch \
    file://distros-add-windriver-skeleton-distro-file.patch \
    file://cloud.cfg.tmpl-fix-the-render-exception.patch \
    \
    file://rh/0001-Add-initial-redhat-setup.patch \
    file://rh/0002-Do-not-write-NM_CONTROLLED-no-in-generated-interface.patch \
    file://rh/0003-limit-permissions-on-def_log_file.patch \
    file://rh/0004-remove-tee-command-from-logging-configuration.patch \
    file://rh/0005-add-power-state-change-module-to-cloud_final_modules.patch \
    file://rh/0006-azure-ensure-that-networkmanager-hook-script-runs.patch \
    file://rh/0007-sysconfig-Don-t-write-BOOTPROTO-dhcp-for-ipv6-dhcp.patch \
    file://rh/0008-DataSourceAzure.py-use-hostnamectl-to-set-hostname.patch \
    file://rh/0009-sysconfig-Don-t-disable-IPV6_AUTOCONF.patch \
    file://rh/ci-Adding-systemd-mount-options-to-wait-for-cloud-init.patch \
    file://rh/ci-Azure-Ignore-NTFS-mount-errors-when-checking-ephemer.patch \
    file://rh/ci-azure-Add-reported-ready-marker-file.patch \
    file://rh/ci-Adding-disk_setup-to-rhel-cloud.cfg.patch \
    file://rh/cloud-init-centos-user.patch  \
    file://rh/cloud-init-17.1-no-override-default-network.patch \
    \
    file://distros-rhel.py-changes-for-wrl.patch \
"

SRC_URI[md5sum] = "1fa81054101f3432340026fc210d4917"
SRC_URI[sha256sum] = "0224969ebdae6eadffc5f40823bb206d8b05d99a1b730018535102f38b155249"

S = "${WORKDIR}/${BPN}-${PV}"

inherit setuptools update-rc.d systemd

DISTUTILS_INSTALL_ARGS_append = " \
    -O1 --skip-build \
"

do_install_append() {
    install -d ${D}/var/lib/cloud

    # /run/cloud-init needs a tmpfiles.d entry
    install -d ${D}${sysconfdir}/tmpfiles.d
    install -m 0644 ${S}/rhel/cloud-init-tmpfiles.conf ${D}${sysconfdir}/tmpfiles.d/${BPN}.conf

    # We supply our own config file since our software differs from Ubuntu's.
    install -m 0644 ${S}/rhel/cloud.cfg ${D}/${sysconfdir}/cloud/cloud.cfg

    install -d ${D}/${sysconfdir}/rsyslog.d
    install -m 0644 ${S}/tools/21-cloudinit.conf ${D}/${sysconfdir}/rsyslog.d/21-cloudinit.conf

    # Make installed NetworkManager hook name less generic
    mv ${D}/etc/NetworkManager/dispatcher.d/hook-network-manager \
       ${D}/etc/NetworkManager/dispatcher.d/cloud-init-azure-hook

    # Install redhat systemd units
    install -d ${D}${systemd_system_unitdir}
    cp -av --no-preserve=ownership ${S}/rhel/systemd/* ${D}${systemd_system_unitdir}/
}

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${BPN} = "cloud-init"

SYSTEMD_SERVICE_${PN} = "\
    cloud-init-local.service \
    cloud-init.service \
    cloud-config.service \
    cloud-config.target \
    cloud-final.service \
"
SYSTEMD_AUTO_ENABLE_${PN} = "enable"

FILES_${PN} += " \
    ${systemd_unitdir} \
"

RDEPENDS_${PN} = " \
    e2fsprogs \
    iproute2 \
    net-tools \
    procps \
    python-jinja2 \
    python-jsonpatch \
    python-prettytable \
    python-pyserial \
    python-pyyaml \
    python-requests \
    python-six \
    rsyslog \
    shadow \
"
