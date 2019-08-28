DESCRIPTION = "OpenStack Baremetal Hypervisor(ironic) service"
HOMEPAGE = "https://launchpad.net/ironic"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

SRCNAME = "ironic"
RDO_VER = "queens-rdo"

SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/queens \
    https://github.com/rdo-packages/ironic-distgit/archive/${RDO_VER}.zip \
    file://0001-floppy-support.patch \
    "

SRC_URI[sha256sum] = "c0bd46b327a9b64dd3282f0eb82661a99c13da615b21fe981e65ac00098db385"

SRCREV = "21b199b586959cdedd92543a3d1bf2252907f67c"
PV = "10.1.4+git${SRCPV}"
S = "${WORKDIR}/git"

#rdo_hash = "399a4ca416f838d6fdc7f6c3bdbc28dd31058635"

inherit setuptools systemd useradd identity default_configs hosts monitor

USER = "ironic"
GROUP = "ironic"

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system ${GROUP}"
USERADD_PARAM_${PN} = "--system -m -d ${localstatedir}/lib/ironic -s /bin/false -g ${GROUP} ${USER}"

PACKAGES += "${SRCNAME}-tests ${SRCNAME}-api ${SRCNAME}-conductor ${SRCNAME}-common ${SRCNAME}"
ALLOW_EMPTY_${SRCNAME}-api = "1"
ALLOW_EMPTY_${SRCNAME}-conductor = "1"

RDEPENDS_${SRCNAME}-tests += " bash python"

FILES_${PN} = "${libdir}/* /etc/tgt"

FILES_${SRCNAME}-tests = "${sysconfdir}/${SRCNAME}/tools"

FILES_${SRCNAME}-api = " \
    ${bindir}/ironic-api \
    ${systemd_unitdir}/system/openstack-ironic-api.service \    
"

FILES_${SRCNAME}-conductor = " \
    ${bindir}/ironic-conductor \
    ${systemd_unitdir}/system/openstack-ironic-conductor.service \    
"

FILES_${SRCNAME}-common = "${bindir}/* \
    ${sysconfdir}/${SRCNAME}/* \
    ${localstatedir}/* \
    ${sysconfdir}/${SRCNAME}/drivers/* \
    ${datadir}/* \
    ${sysconfdir}/sudoers.d \
    "

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} += " \
        ipmitool \
        python-pysendfile \
        python-alembic \
        python-automaton \
        python-cinderclient \
        python-dracclient \
        python-eventlet \
        python-futurist \
        python-glanceclient \
        python-ironic-inspector-client \
        python-ironic-lib \
        python-jinja2 \
        python-jsonpatch \
        python-jsonschema \
        python-keystoneauth1 \
        python-keystonemiddleware \
        python-neutronclient \
        python-oslo.concurrency \
        python-oslo.config \
        python-oslo.context \
        python-oslo.db \
        python-oslo.i18n \
        python-oslo.log \
        python-oslo.messaging \
        python-oslo.middleware \
        python-oslo.policy \
        python-oslo.reports \
        python-oslo.rootwrap \
        python-oslo.serialization \
        python-oslo.service \
        python-oslo.utils \
        python-oslo.versionedobjects \
        python-osprofiler \
        python-os-traits \
        python-pbr \
        python-pecan \
        python-proliantutils \
        python-psutil \
        python-requests \
        python-retrying \
        python-rfc3986 \
        python-scciclient \
        python-six \
        python-sqlalchemy \
        python-stevedore \
        python-sushy \
        python-swiftclient \
        python-tooz \
        python-ucssdk \
        python-webob \
        python-wsme \
        python-pysnmp \
        python-pytz \
        "

RDEPENDS_${SRCNAME} = " \
    ${PN} \
    postgresql \
    postgresql-client \
    python-psycopg2 \
    tgt"

RDEPENDS_${SRCNAME}-api = "${SRCNAME}"
RDEPENDS_${SRCNAME}-conductor = "${SRCNAME}"

RDEPENDS_${PN}-dev_append = "systemd"

do_install_append() {

        #install -p -D -m 644 ${WORKDIR}/ironic-distgit-${RDO_VER}/ironic.logrotate ${D}${sysconfdir}/logrotate.d/openstack-ironic

        if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
            # install systemd scripts
            install -d ${D}${systemd_unitdir}/system
            install -p -D -m 644 ${WORKDIR}/ironic-distgit-${RDO_VER}/openstack-ironic-api.service ${D}${systemd_unitdir}/system
            install -p -D -m 644 ${WORKDIR}/ironic-distgit-${RDO_VER}/openstack-ironic-conductor.service ${D}${systemd_unitdir}/system
        fi
        
        
        # install sudoers file
        mkdir -p ${D}${sysconfdir}/sudoers.d
        install -p -D -m 440 ${WORKDIR}/ironic-distgit-${RDO_VER}/ironic-rootwrap-sudoers ${D}${sysconfdir}/sudoers.d/ironic-rootwrap
        chown root:root ${D}${sysconfdir}/sudoers.d/ironic-rootwrap
        
        mkdir -p ${D}${datadir}/ironic/
        mkdir -p ${D}${localstatedir}/log/ironic/
        #mkdir -p ${D}${sysconfdir}/ironic/rootwrap.d
        install -d ${D}${sysconfdir}/ironic/rootwrap.d
        
        #Populate the conf dir
        install -p -D -m 640 tools/config/ironic-config-generator.conf ${D}/${datadir}/ironic/ironic-config-generator.conf
        install -p -D -m 640 tools/policy/ironic-policy-generator.conf ${D}/${datadir}/ironic/ironic-policy-generator.conf
        mv ${D}${datadir}/etc/ironic/rootwrap.conf ${D}/${sysconfdir}/ironic/rootwrap.conf
        mv ${D}${datadir}/etc/ironic/rootwrap.d/* ${D}/${sysconfdir}/ironic/rootwrap.d/
        # Remove duplicate config files under /usr/etc/ironic
        rmdir ${D}${datadir}/etc/ironic/rootwrap.d
        rmdir ${D}${datadir}/etc/ironic
        rmdir ${D}${datadir}/etc
        
        # Install distribution config
        install -p -D -m 640 ${WORKDIR}/ironic-distgit-${RDO_VER}/ironic-dist.conf ${D}/${datadir}/ironic/ironic-dist.conf
        
        # Install i18n .mo files (.po and .pot are not required)
        install -d -m 755 ${D}${datadir}
        rm -f ${D}${libdir}/python${PYTHON_MAJMIN}/ironic/locale/*/LC_*/ironic*po
        rm -f ${D}${libdir}/python${PYTHON_MAJMIN}/ironic/locale/*pot
        #mv ${D}${libdir}/python${PYTHON_MAJMIN}/ironic/locale ${D}${datadir}/locale
        mv ${D}${libdir}/${PYTHON_DIR}/site-packages/ironic/locale ${D}${datadir}/locale



}

pkg_postinst_${SRCNAME}-common () {
    if [ -z "$D" ] && [ -e /usr/bin/oslo-config-generator ] ; then
        /usr/bin/oslo-config-generator --config-file ${datadir}/ironic/ironic-config-generator.conf --output-file ${sysconfdir}/ironic/ironic.conf
    fi
    if [ -z "$D" ] && [ -e /usr/bin/oslopolicy-sample-generator ] ; then
        /usr/bin/oslopolicy-sample-generator --config-file  ${datadir}/ironic/ironic-policy-generator.conf --output-file ${sysconfdir}/ironic/policy.json
    fi
}

