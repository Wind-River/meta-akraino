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

SUMMARY = "This includes helm charts, supplementary utils, ansible playbooks for caas"
DESCRIPTION = "\
  This includes the following: \
  - the necessary playbooks to instantiate the caas subsystem. \
  - the supplementary utils for caas subsystem. \
  - the necessary helm charts to deploy the caas subsystem. \
"
HOMEPAGE = "https://gerrit.akraino.org/r/ta/caas-install"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c8c61a9e78acc1e713ef4c6c14e14e54"

DEPENDS = "rsync-native"

PROTOCOL = "https"
SRCREV = "b4f64181a4fa5e20fc28ae727beeee90852bcbea"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-install;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

PV = "${MAJOR_VERSION}+git${SRCREV}"

PACKAGES =+ "caas-utils caas-infra-charts caas-instantiate"

MAJOR_VERSION = "1.0.0"

PKGV_caas-utils = "${MAJOR_VERSION}-10"
PKGV_caas-instantiate = "${MAJOR_VERSION}-20"
PKGV_caas-infra-charts = "${MAJOR_VERSION}-34"

do_install() {
	# install for caas-utils
	install -d ${D}${libexecdir}/caas
	install -d ${D}${sysconfdir}/logrotate.d

	install -m 0700 ${S}/utils/deploy/merge_image.sh ${D}${libexecdir}/caas

	# install for caas-infra-charts
	install -d ${D}${playbooks_path}
	install -m 0755 ${S}/ansible/playbooks/install_caas_infra.yaml ${D}${playbooks_path}
	install -m 0755 ${S}/ansible/playbooks/infra_chart_reconfig_fluentd.yaml ${D}${playbooks_path}

	install -d ${D}${roles_path}
	rsync -av --no-owner ${S}/ansible/roles/install_caas_infra ${D}${roles_path}
	rsync -av --no-owner ${S}/ansible/roles/pre_install_caas_infra ${D}${roles_path}
	rsync -av --no-owner ${S}/ansible/roles/infra_chart_reconfig ${D}${roles_path}

	install -d ${D}${caas_chart_path}
	rsync -av --no-owner ${S}/infra-charts/* ${D}${caas_chart_path}

	# install for caas-instantiate
	rsync -av --no-owner ${S}/ansible/playbooks/caas_cleanup.yaml ${D}/${playbooks_path}/
	rsync -av --no-owner ${S}/ansible/playbooks/cloud_admin_user.yaml ${D}/${playbooks_path}/
	rsync -av --no-owner ${S}/ansible/playbooks/common.yaml ${D}/${playbooks_path}/
	rsync -av --no-owner ${S}/ansible/playbooks/docker.yaml ${D}/${playbooks_path}/
	rsync -av --no-owner ${S}/ansible/playbooks/image_push.yaml ${D}/${playbooks_path}/
	rsync -av --no-owner ${S}/ansible/playbooks/openrc_hack.yaml ${D}/${playbooks_path}/
	rsync -av --no-owner ${S}/ansible/playbooks/pre_config_all.yaml ${D}/${playbooks_path}/

	rsync -av --no-owner ${S}/ansible/roles/caas_cleanup ${D}/${roles_path}/
	rsync -av --no-owner ${S}/ansible/roles/cloud_admin_user ${D}/${roles_path}/
	rsync -av --no-owner ${S}/ansible/roles/common_tasks ${D}/${roles_path}/
	rsync -av --no-owner ${S}/ansible/roles/docker ${D}/${roles_path}/
	rsync -av --no-owner ${S}/ansible/roles/docker_image_load ${D}/${roles_path}/
	rsync -av --no-owner ${S}/ansible/roles/docker_image_push ${D}/${roles_path}/
	rsync -av --no-owner ${S}/ansible/roles/manifests ${D}/${roles_path}/
	rsync -av --no-owner ${S}/ansible/roles/nodeconf ${D}/${roles_path}/
	rsync -av --no-owner ${S}/ansible/roles/pre_config_all ${D}/${roles_path}/
	rsync -av --no-owner ${S}/ansible/roles/log ${D}/${roles_path}/

	install -d ${D}${ansible_filter_plugins_path}
	rsync -av --no-owner ${S}/ansible/filter_plugins/* ${D}${ansible_filter_plugins_path}

	install -d ${D}${ansible_modules_path}
	rsync -av --no-owner ${S}/ansible/library/* ${D}${ansible_modules_path}

	install -d ${D}${cm_config_dir}
	rsync -av --no-owner ${S}/cm_config/caas.yaml ${D}${cm_caas_config_file}

	sed -ri -e 's/^crf_chart_path/caas_chart_path/' \
	        -e '/^caas_base_directory/{s|:.*|: ${caas_path}|}' \
	        -e '/^infra_containers_directory/{s|:.*|: ${caas_container_tar_path}|}' \
	        -e '/^manifests_directory/{s|:.*|: ${caas_manifest_path}|}' \
	        -e '/^rbac_manifests_directory/{s|:.*|: ${caas_rbac_manifests_path}|}' \
	        -e '/^caas_chart_path/{s|:.*|: ${caas_chart_path}|}' \
	        -e '/^libexec_dir/{s|:.*|: ${caas_libexec_path}|}' \
	        -e '/^danm_crd_dir/{s|:.*|: ${caas_danm_crd_path}|}' \
	        ${D}${cm_caas_config_file}

	chown -R root:root ${D}
}


pkg_postinst_caas-utils () {
	find ${libdir}/debug/usr/ -xtype l -exec rm -f {} \;
}

pkg_postinst_caas-infra-charts () {
	mkdir -p ${postconfig_path}
	ln -sf ${playbooks_path}/install_caas_infra.yaml ${postconfig_path}
}

pkg_postrm_caas-infra-charts () {
	rm -f ${postconfig_path}/install_caas_infra.yaml
}

pkg_postinst_caas-instantiate () {
	mkdir -p ${postconfig_path}

	ln -sf ${playbooks_path}/cloud_admin_user.yaml ${postconfig_path}/
	ln -sf ${playbooks_path}/common.yaml           ${postconfig_path}/
	ln -sf ${playbooks_path}/docker.yaml           ${postconfig_path}/
	ln -sf ${playbooks_path}/image_push.yaml       ${postconfig_path}/
	ln -sf ${playbooks_path}/openrc_hack.yaml      ${postconfig_path}/
	ln -sf ${playbooks_path}/pre_config_all.yaml   ${postconfig_path}/

	mkdir -p ${finalize_path}/
	ln -sf ${playbooks_path}/caas_cleanup.yaml     ${finalize_path}/
}

pkg_postrm_caas-instantiate () {
	if [ $1 == 0 ]; then
		rm -f ${postconfig_path}/cloud_admin_user.yaml
		rm -f ${postconfig_path}/common.yaml
		rm -f ${postconfig_path}/docker.yaml
		rm -f ${postconfig_path}/image_push.yaml
		rm -f ${postconfig_path}/openrc_hack.yaml
		rm -f ${postconfig_path}/pre_config_all.yaml
		rm -f ${finalize_path}/caas_cleanup.yaml
	fi
}

FILES_caas-utils = " \
    ${libexecdir}/caas/merge_image.sh \
"

FILES_caas-infra-charts = " \
    ${playbooks_path}/install_caas_infra.yaml \
    ${playbooks_path}/infra_chart_reconfig_fluentd.yaml \
    ${roles_path}/install_caas_infra \
    ${roles_path}/pre_install_caas_infra \
    ${roles_path}/infra_chart_reconfig \
    ${caas_chart_path} \
"

FILES_caas-instantiate = " \
    ${playbooks_path} \
    ${roles_path} \
    ${ansible_filter_plugins_path} \
    ${ansible_modules_path} \
    ${cm_config_dir} \
"

RDEPENDS_${PN} = " \
    caas-utils \
    caas-instantiate \
    caas-infra-charts \
"
RDEPENDS_caas-utils = "bash"
RDEPENDS_caas-instantiate = "python python-ansible"
RDEPENDS_caas-infra-charts = "python-ansible"

ALLOW_EMPTY_${PN} = "1"
