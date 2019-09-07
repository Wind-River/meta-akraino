SUMMARY = "Containers as a Service Kube DNS component"
DESCRIPTION = "This contains the kubedns container image, \
  and related deployment artifacts for the CaaS subsystem. \
"
HOMEPAGE = "https://github.com/kubernetes/dns"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ff253ad767462c46be284da12dda33e8"

PROTOCOL = "https"
SRCREV = "b829734396262b0158289449d812894cb3624707"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-kubedns;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "1.15.4"
MINOR_VERSION = "2"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "kubedns"
DOCKER_EXTRA_ARG += "--build-arg KUBEDNS=${MAJOR_VERSION}"

do_install_append () {
	install -d ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/${COMPONENT} ${D}${roles_path}

	install -D ${S}/ansible/playbooks/${COMPONENT}.yaml ${D}/${playbooks_path}/${COMPONENT}.yaml
}

pkg_postinst_ontarget_${PN} () {
	mkdir -p ${postconfig_path}
	ln -s ${playbooks_path}/${COMPONENT}.yaml ${postconfig_path}/
}

pkg_postrm_${PN} () {
	if [ $1 -eq 0 ]; then
		rm -f ${postconfig_path}/${COMPONENT}.yaml
	fi
}

FILES_${PN} += "\
    ${roles_path} \
    ${playbooks_path} \
"
