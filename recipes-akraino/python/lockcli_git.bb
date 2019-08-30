DESCRIPTION = "This RPM contains source code for the lock cli"
HOMEPAGE = "https://gerrit.akraino.org/r/ta/lockcli"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "54aed66cdaef170ffbfdcb511ced241f4be14555"
S = "${WORKDIR}/git/src"

SRC_URI = "git://gerrit.akraino.org/r/ta/lockcli.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit setuptools akraino-version

RDEPENDS_${PN} += " \
        etcd \
        python-etcd \
        "
