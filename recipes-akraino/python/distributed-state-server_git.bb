DESCRIPTION = "distributed state server and its plugins"
HOMEPAGE = "https://gerrit.akraino.org/r/ta/distributed-state-server"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "bd5a0a173f1ae9c64782fbf47565cc26ed23b448"
S = "${WORKDIR}/git/src"

SRC_URI = "git://gerrit.akraino.org/r/ta/distributed-state-server.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit setuptools akraino-version

RDEPENDS_${PN} += " \
        etcd \
        python-etcd \
        "
