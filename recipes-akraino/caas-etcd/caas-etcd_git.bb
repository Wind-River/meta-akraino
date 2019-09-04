SUMMARY = "Containers as a Service etcd component"
HOMEPAGE = "https://gerrit.akraino.org/r/ta/caas-etcd"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

PROTOCOL = "https"
SRCREV = "26fbfdd8d2c6c526620cc848a22483035270965a"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-etcd;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "3.3.13"
MINOR_VERSION = "5"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "etcd"
DOCKER_EXTRA_ARG += "--build-arg ETCD_VERSION=${MAJOR_VERSION}"

do_install_append () {
	install -d ${D}${roles_path}
	rsync -rlpD ${S}/ansible/roles/* ${D}${roles_path}

	install -D ${S}/ansible/playbooks/etcd.yaml ${D}/${playbooks_path}/etcd.yaml
}

pkg_postinst_${PN} () {
	mkdir -p ${postconfig_path}
	ln -s ${playbooks_path}/etcd.yaml ${postconfig_path}/
}

pkg_postrm_${PN} () {
	if [ $1 -eq 0 ]; then
		rm -f ${postconfig_path}/etcd.yaml
	fi
}

FILES_${PN} += "\
    ${roles_path} \
    ${playbooks_path} \
"
