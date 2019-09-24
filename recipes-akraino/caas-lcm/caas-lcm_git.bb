DESCRIPTION = "This RPM contains Life Cycle Managemnet workflows for the CaaS subsystem."
HOMEPAGE = "https://gerrit.akraino.org/r/ta/caas-lcm"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ff253ad767462c46be284da12dda33e8"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "f99db47cea8dd3fc87268c83f7e530d10d27456c"
S = "${WORKDIR}/git/"
GIT = "${WORKDIR}/git"


SRC_URI = "git://gerrit.akraino.org/r/ta/${PN}.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit akraino-version

DEPENDS = "rsync-native"

SU_PATH = "${caas_lcm_path}/su"
DEPLOY_PATH = "${caas_lcm_path}/deploy"

do_install() {
    mkdir -p ${D}/${SU_PATH}/
    rsync -rlpD su/* ${D}/${SU_PATH}/

    mkdir -p ${D}/${DEPLOY_PATH}/
    rsync -rlpD deploy/* ${D}/${DEPLOY_PATH}/

    mkdir -p ${D}/${playbooks_path}/
    rsync -rlpD ansible/playbooks/pre_config_lcm.yaml ${D}/${playbooks_path}/

    mkdir -p ${D}/${roles_path}/
    rsync -rlpD ansible/roles/pre_config_lcm ${D}/${roles_path}/

    sed -i 's|{{ lcm_path }}|${caas_lcm_path}|g' ${D}/${DEPLOY_PATH}/roles/bm_onboard/tasks/main.yml
    sed -i 's|{{ lcm_path }}|${caas_lcm_path}|g' ${D}/${DEPLOY_PATH}/roles/list_application_deployments/tasks/main.yml
    sed -i 's|{{ lcm_path }}|${caas_lcm_path}|g' ${D}/${DEPLOY_PATH}/roles/list_application_packages/tasks/main.yml
    sed -i 's|{{ lcm_path }}|${caas_lcm_path}|g' ${D}/${DEPLOY_PATH}/roles/list_docker_images/tasks/main.yml
    sed -i 's|{{ caas_manifest_path }}|${caas_manifest_path}|g' ${D}/${DEPLOY_PATH}/group_vars/controller-1.caas_master/params.yml
    sed -i 's|{{ lcm_path }}|${caas_lcm_path}|g' ${D}/${roles_path}/pre_config_lcm/tasks/main.yml
}

pkg_postinst_ontarget_${PN} () {
    mkdir -p ${postconfig_path}/
    ln -sf ${playbooks_path}/pre_config_lcm.yaml ${postconfig_path}/
}

pkg_postrm_${PN} () {
    if [ $1 -eq 0 ]; then
        rm -f ${postconfig_path}/pre_config_lcm.yaml
    fi
}

FILES_${PN} = " \
    /opt   \
    /etc   \
"

RDEPENDS_${PN} = "bash"
