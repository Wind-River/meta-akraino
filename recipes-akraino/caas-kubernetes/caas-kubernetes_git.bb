SUMMARY = "Containers as a Service kubernetes component"
DESCRIPTION = "This contains the kubernetes container for CaaS subsystem. \
  This container contains the kubernetes service. \
"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

PROTOCOL = "https"
SRCREV = "fd5f1b3951484a74b955084468dda3a334cc0648"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-kubernetes;protocol=${PROTOCOL}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "1.15.2"
MINOR_VERSION = "1"
KUBERNETESPAUSE_VERSION = "3.1"
GO_VERSION = "1.12.1"
CEPH_VERSION = "12.2.5"

binary_build_dir = "${B}/binary-save"
built_binaries_dir = "/binary-save"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "kubernetes"
KUBERNETES_EXTRA_ARG = ' \
    --build-arg KUBERNETES_VERSION=${MAJOR_VERSION} \
    --build-arg go_version=${GO_VERSION} \
    --build-arg binaries=${built_binaries_dir} \
'

HYPERKUBE_EXTRA_ARG = " \
    --build-arg ceph_version=${CEPH_VERSION} \
"

KUBERNETESPAUS_EXTRA_ARG = " \
    --build-arg KUBERNETESPAUSE_VERSION=${KUBERNETESPAUSE_VERSION} \
"

do_compile () {
	# Build kubernetes binaries
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		${KUBERNETES_EXTRA_ARG} \
		--tag kubernetes-builder:${IMAGE_TAG} \
		${docker_build_dir}/kubernetes-builder

	builder_container=$(${docker_bin} run -id --rm --network=none --entrypoint=/bin/sh kubernetes-builder:${IMAGE_TAG})
	mkdir -p ${binary_build_dir}
	${docker_bin} cp ${builder_container}:${built_binaries_dir}/kubelet ${binary_build_dir}/
	${docker_bin} cp ${builder_container}:${built_binaries_dir}/kubectl ${binary_build_dir}/
	${docker_bin} cp ${builder_container}:${built_binaries_dir}/kube-apiserver ${binary_build_dir}/
	${docker_bin} cp ${builder_container}:${built_binaries_dir}/kube-controller-manager ${binary_build_dir}/
	${docker_bin} cp ${builder_container}:${built_binaries_dir}/kube-proxy ${binary_build_dir}/
	${docker_bin} cp ${builder_container}:${built_binaries_dir}/kube-scheduler ${binary_build_dir}/

	${docker_bin} rm -f ${builder_container}
	${docker_bin} rmi kubernetes-builder:${IMAGE_TAG}

	# Build hyperkube container image
	rsync -rlpD ${binary_build_dir}/kube-apiserver ${docker_build_dir}/hyperkube/
	rsync -rlpD ${binary_build_dir}/kube-controller-manager ${docker_build_dir}/hyperkube/
	rsync -rlpD ${binary_build_dir}/kube-proxy ${docker_build_dir}/hyperkube/
	rsync -rlpD ${binary_build_dir}/kube-scheduler ${docker_build_dir}/hyperkube/

	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		${HYPERKUBE_EXTRA_ARG} \
		--tag hyperkube:${IMAGE_TAG} \
		${docker_build_dir}/hyperkube

	mkdir -p ${docker_save_dir}
	${docker_bin} save hyperkube:${IMAGE_TAG} | xz -z -T2 > "${docker_save_dir}/hyperkube:${IMAGE_TAG}.tar"
	${docker_bin} rmi hyperkube:${IMAGE_TAG}

	# Build kubernetes pause container image
	${docker_bin} build \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		${KUBERNETESPAUS_EXTRA_ARG} \
		--tag kubernetespause:${IMAGE_TAG} \
		${docker_build_dir}/kubernetespause

	mkdir -p ${docker_save_dir}
	${docker_bin} save kubernetespause:${IMAGE_TAG} | xz -z -T2 > "${docker_save_dir}/kubernetespause:${IMAGE_TAG}.tar"
	${docker_bin} rmi kubernetespause:${IMAGE_TAG}
}

do_install () {
	install -d ${D}${caas_container_tar_path}
	rsync -rlpD ${docker_save_dir}/* ${D}${caas_container_tar_path}

	install -d ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/* ${D}${roles_path}

	install -d ${D}/${playbooks_path}
	rsync -rlpD ${S}/ansible/playbooks/* ${D}/${playbooks_path}

	install -D -m 0755 ${binary_build_dir}/kubectl ${D}${bindir}/kubectl
	install -D -m 0755 ${binary_build_dir}/kubelet ${D}${bindir}/kubelet
}

pkg_postinst_ontarget_${PN} () {
	mkdir -p ${postconfig_path}
	ln -s ${playbooks_path}/${COMPONENT}.yaml ${postconfig_path}/
	ln -s ${playbooks_path}/bootstrap_kube_proxy.yaml          ${postconfig_path}/
	ln -s ${playbooks_path}/bootstrap_kubelet.yaml             ${postconfig_path}/
	ln -s ${playbooks_path}/kube_master.yaml                   ${postconfig_path}/
	ln -s ${playbooks_path}/kube_secret_key_creation.yaml      ${postconfig_path}/
	ln -s ${playbooks_path}/kube_secret_key_distribution.yaml  ${postconfig_path}/
	ln -s ${playbooks_path}/kube_token_creation.yaml           ${postconfig_path}/
	ln -s ${playbooks_path}/kube_token_distribution.yaml       ${postconfig_path}/
	ln -s ${playbooks_path}/kubernetes_ceph.yaml               ${postconfig_path}/
	ln -s ${playbooks_path}/master_kube_proxy.yaml             ${postconfig_path}/
	ln -s ${playbooks_path}/master_kubelet.yaml                ${postconfig_path}/
	ln -s ${playbooks_path}/service_account_creation.yaml      ${postconfig_path}/
	ln -s ${playbooks_path}/service_account_distribution.yaml  ${postconfig_path}/
}

pkg_postrm_${PN} () {
	if [ $1 -eq 0 ]; then
		rm -f ${postconfig_path}/${COMPONENT}.yaml
		rm -f %{postconfig_path}/bootstrap_kube_proxy.yaml
		rm -f %{postconfig_path}/bootstrap_kubelet.yaml
		rm -f %{postconfig_path}/kube_master.yaml
		rm -f %{postconfig_path}/kube_secret_key_creation.yaml
		rm -f %{postconfig_path}/kube_secret_key_distribution.yaml
		rm -f %{postconfig_path}/kube_token_creation.yaml
		rm -f %{postconfig_path}/kube_token_distribution.yaml
		rm -f %{postconfig_path}/kubernetes_ceph.yaml
		rm -f %{postconfig_path}/master_kube_proxy.yaml
		rm -f %{postconfig_path}/master_kubelet.yaml
		rm -f %{postconfig_path}/service_account_creation.yaml
		rm -f %{postconfig_path}/service_account_distribution.yaml
	fi
}

FILES_${PN} += "\
    ${bindir} \
    ${roles_path} \
    ${playbooks_path} \
"

INSANE_SKIP_${PN} = "already-stripped"
