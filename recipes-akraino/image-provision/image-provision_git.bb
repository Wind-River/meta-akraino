DESCRIPTION = "Contains dracut modules used for provisioning master image from a boot CD."
HOMEPAGE = "https://gerrit.akraino.org/r/ta/image-provision"
SECTION = "devel/utils"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "b5a73c42ad43ce29f7ceb6c5997d30476f4e931a"
S = "${WORKDIR}/git"

SRC_URI = "git://gerrit.akraino.org/r/ta/image-provision.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit akraino-version

DEPENDS += " \
        bash \
        rsync-native \
        "

RDEPENDS_${PN} += " \
        bash \
        cloud-init \
        dracut \
        "

do_install() {

        mkdir -p ${D}/usr/lib/dracut/modules.d/
        mkdir -p ${D}/etc/
        
        rsync -rlpD dracut/modules/00installmedia ${D}/usr/lib/dracut/modules.d/
        rsync -rlpD dracut/modules/00readfloppyconf ${D}/usr/lib/dracut/modules.d/
        rsync -rlpD dracut/modules/00readcdconf ${D}/usr/lib/dracut/modules.d/
        rsync -rlpD dracut/modules/00netconfig ${D}/usr/lib/dracut/modules.d/
        rsync -rlpD dracut/conf/dracut-provision.conf  ${D}/etc/

}

FILES_${PN} = "${libdir}/dracut/modules.d/* /etc/*"
