DESCRIPTION = "Storage ansible for akraino"
HOMEPAGE = "https://wiki.akraino.org/pages/viewpage.action?pageId=6128402"
SECTION = "devel/ansible"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "821dc536b213b80ebee1a936d5977b233e5f7eb1"
S = "${WORKDIR}/git"

SRC_URI = "git://gerrit.akraino.org/r/ta/storage.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit akraino-version

DEPENDS += " \
        rsync-native \
        "
do_install() {

        mkdir -p ${D}${sysconfdir}/opt/localstorage
        rsync -rlpD partfs_rootdisk/localstorage/* ${D}${sysconfdir}/opt/localstorage


}


