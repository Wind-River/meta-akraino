DESCRIPTION = "This rpm contains the necessary security related playbooks + manifests for the caas subsystem."
HOMEPAGE = "https://gerrit.akraino.org/r/ta/caas-security"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c8c61a9e78acc1e713ef4c6c14e14e54"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "429f53ca35c087ea854a2987c3b56e0916526bec"
S = "${WORKDIR}/git/"
GIT = "${WORKDIR}/git"


SRC_URI = "git://gerrit.akraino.org/r/ta/${PN}.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit akraino-version

DEPENDS = "rsync-native"

do_install() {
    mkdir -p ${D}/${caas_rbac_manifests_path}/
    rsync -rlpD rbac_manifests/* ${D}/${caas_rbac_manifests_path}/

    mkdir -p ${D}/${playbooks_path}/
    rsync -rlpD ansible/playbooks/* ${D}/${playbooks_path}/

    mkdir -p ${D}/${roles_path}/
    rsync -rlpD ansible/roles/* ${D}/${roles_path}/
}

pkg_postinst_ontarget_${PN} () {
    mkdir -p ${postconfig_path}/
    ln -sf ${playbooks_path}/rbac.yaml     ${postconfig_path}
    ln -sf ${playbooks_path}/security.yaml ${postconfig_path}
}

pkg_postrm_${PN} () {
    if [ $1 -eq 0 ]; then
        rm -f ${postconfig_path}/rbac.yaml
        rm -f ${postconfig_path}/security.yaml
    fi
}

FILES_${PN} = " \
    ${caas_rbac_manifests_path}/* \
    ${playbooks_path}/* \
    ${roles_path}/*     \
    ${systemd_system_unitdir}/ \
"

RDEPENDS_${PN} = "bash"
