SUMMARY = "Containers as a Service helm component"
DESCRIPTION = "This contains the helm container for CaaS subsystem."

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

PROTOCOL = "https"
SRCREV = "5c8e0ac9f56da49dffcd4f3200d54f615a395cce"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-helm;protocol=${PROTOCOL}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "2.14.3"
MINOR_VERSION = "2"
GO_VERSION = "1.12.9"

binary_build_dir = "${B}/binary-save"
built_binaries_dir = "/binary-save"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "helm"
HELM_EXTRA_ARG = ' \
    --build-arg HELM_VERSION=${MAJOR_VERSION} \
    --build-arg go_version=${GO_VERSION} \
    --build-arg binaries=${built_binaries_dir} \
'

do_compile () {
	# Build HELM binaries
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		${HELM_EXTRA_ARG} \
		--tag helm-builder:${IMAGE_TAG} \
		${docker_build_dir}/helm-builder

	mkdir -p ${binary_build_dir}
	builder_container=$(${docker_bin} run -id --rm --network=none --entrypoint=/bin/sh helm-builder:${IMAGE_TAG})
	${docker_bin} cp ${builder_container}:${built_binaries_dir}/helm   ${binary_build_dir}/
	${docker_bin} cp ${builder_container}:${built_binaries_dir}/tiller ${binary_build_dir}/
	${docker_bin} rm -f ${builder_container}
	${docker_bin} rmi helm-builder:${IMAGE_TAG}

	# Build tiller contaienr image
	rsync -rlpD ${binary_build_dir}/* ${docker_build_dir}/tiller/
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		--tag tiller:${IMAGE_TAG} \
		${docker_build_dir}/tiller

	mkdir -p ${docker_save_dir}
	$docker_bin} save tiller:${IMAGE_TAG} | xz -z -T2 > "${docker_save_dir}/tiller:${IMAGE_TAG}.tar"
	${docker_bin} rmi tiller:${IMAGE_TAG}
}

do_install_append () {
	install -d ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/${COMPONENT} ${D}${roles_path}

	install -D ${S}/ansible/playbooks/${COMPONENT}.yaml ${D}/${playbooks_path}/${COMPONENT}.yaml
	install -D -m 0755 ${binary_build_dir}/${COMPONENT} ${S}/${bindir}/${COMPOENT}
}

pkg_postinst_ontarget_${PN} () {
	mkdir -p ${postconfig_path}
	ln -s ${playbooks_path}/${COMPOENT}.yaml ${postconfig_path}/
}

pkg_postrm_${PN} () {
	if [ $1 -eq 0 ]; then
		rm -f ${postconfig_path}/${COMPONENT}.yaml
	fi
}


FILES_${PN} += "\
    ${bindir} \
    ${roles_path} \
    ${playbooks_path} \
"
