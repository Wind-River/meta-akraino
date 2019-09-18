SUMMARY = "Containers as a Service Swift component"
DESCRIPTION = "This contains the swift container and ansible \
  for caas subsystem. This container contains the swift service. \
"
HOMEPAGE = "https://github.com/openstack/swift"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c8c61a9e78acc1e713ef4c6c14e14e54"

PROTOCOL = "https"
SRCREV = "0b89aad6aa6de18c1cc71425fd9f20c97c5c419b"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-registry;protocol=${PROTOCOL}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "2.22.0"
MINOR_VERSION = "0"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "swift"
DOCKER_EXTRA_ARG += " \
    --build-arg SWIFT=${MAJOR_VERSION} \
"

do_install_append () {
	install -d ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/${COMPONENT} ${D}${roles_path}

	install -d ${D}/${playbooks_path}
	rsync -rlpD ${S}/ansible/playbooks/${COMPONENT}.yaml ${D}/${playbooks_path}
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
