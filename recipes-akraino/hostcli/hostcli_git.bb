DESCRIPTION = "This RPM contains source code for the hostcli framework."
HOMEPAGE = "https://wiki.akraino.org/pages/viewpage.action?pageId=6128402"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "9a52ce01e2188bc347d767481bb1a5b2ca3992df"
S = "${WORKDIR}/git/src"

SRC_URI = "git://gerrit.akraino.org/r/ta/hostcli.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit setuptools akraino-version

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} += " \
        python-lxml \
        python-requests \
        python-cython \
        python-cliff \
        python-pip \
        python-osc-lib \
        python-openstackclient \
        "
