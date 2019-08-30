DESCRIPTION = "Ironic drivers for baremetal provisioning using Virtual media for Quanta Hardware and Virtual environment"
HOMEPAGE = "https://docs.openstack.org/ironic/latest/install/enabling-drivers.html"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "9d7df7d094af37b01a0b756becb8521373789ad5"
S = "${WORKDIR}/git/src"

SRC_URI = "git://gerrit.akraino.org/r/ta/ironic-virtmedia-driver.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit setuptools akraino-version

RDEPENDS_${PN} += " \
        python-ironic \
        "
