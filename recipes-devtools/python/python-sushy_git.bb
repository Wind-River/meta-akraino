DESCRIPTION = "Sushy is a Python library to communicate with Redfish based systems"
HOMEPAGE = "https://github.com/openstack/sushy"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

PV = "1.9.0+git${SRCPV}"
SRCREV = "a738cd7ec13c191a479f8fa4dcc3857eb92f5951"

SRCNAME = "sushy"
SRC_URI = "git://github.com/openstack/${SRCNAME}.git;branch=stable/stein"

S = "${WORKDIR}/git"

inherit setuptools

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

# RDEPENDS_default: 
RDEPENDS_${PN} += " \
        python-pbr \
        python-six \
        python-stevedore \
        python-requests \
        python-dateutil \
       "
