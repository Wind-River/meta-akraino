DESCRIPTION = "Pyghmi is a pure Python (mostly IPMI) server management library."
HOMEPAGE = "https://github.com/openstack/pyghmi"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PV = "1.3.0+git${SRCPV}"
SRCREV = "a05c04487424d8240046b7525f412b15e2adbeac"

SRCNAME = "pyghmi"
SRC_URI = "git://github.com/openstack/${SRCNAME}.git;"

S = "${WORKDIR}/git"

inherit setuptools

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

# RDEPENDS_default: 
RDEPENDS_${PN} += " \
        python-cryptography \
       "
