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

SUMMARY = "Containers as a Service chartrepo component"
DESCRIPTION = "This contains the chartrepo container for CaaS subsystem. \
  This container contains the chartrepo service \
"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

PROTOCOL = "https"
SRCREV = "5c8e0ac9f56da49dffcd4f3200d54f615a395cce"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-helm;protocol=${PROTOCOL}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "1.0.0"
MINOR_VERSION = "9"
GO_VERSION = "1.12.9"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "chartrepo"
DOCKER_EXTRA_ARG += " \
    --build-arg go_version=${GO_VERSION} \
"

do_compile () {
	rsync -rlpD ${S}/src/* ${docker_build_dir}/chartrepohandler/ 
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		--tag ${COMPONENT}:${IMAGE_TAG} \
		${docker_build_dir}/chartrepohandler

	mkdir -p ${docker_save_dir}
	${docker_bin} save ${COMPONENT}:${IMAGE_TAG} | xz -z -T2 \
		> ${docker_save_dir}/${COMPONENT}:${IMAGE_TAG}.tar

	${docker_bin} rmi ${COMPONENT}:${IMAGE_TAG}
}

do_install_append () {
	install -d ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/chart_repo ${D}${roles_path}

	install -D ${S}/ansible/playbooks/chart_repo.yaml ${D}/${playbooks_path}/chart_repo.yaml
}

pkg_postinst_ontarget_${PN} () {
	mkdir -p ${postconfig_path}
	ln -s ${playbooks_path}/chart_repo.yaml ${postconfig_path}/
}

pkg_postrm_${PN} () {
	if [ $1 -eq 0 ]; then
		rm -f ${postconfig_path}/chart_repo.yaml
	fi
}

FILES_${PN} += "\
    ${roles_path} \
    ${playbooks_path} \
"
