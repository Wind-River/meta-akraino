DESCRIPTION = "This is a client for the OpenStack Ironic API."
HOMEPAGE = "https://github.com/openstack/python-ironicclient"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

PV = "2.2.2+git${SRCPV}"
SRCREV = "e945946df4b2bbd050c9f7b2ca3b8a3722b1f69b"

SRCNAME = "ironicclient"
SRC_URI = "git://github.com/openstack/python-${SRCNAME}.git;branch=stable/queens"

S = "${WORKDIR}/git"

inherit setuptools

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

# RDEPENDS_default: 
RDEPENDS_${PN} += " \
        bash \
        python-pbr \
        python-appdirs \
        python-dogpile.cache \
        python-jsonschema \
        python-keystoneauth1 \
        python-osc-lib \
        python-oslo.i18n \
        python-oslo.serialization \
        python-oslo.utils \
        python-pyyaml \
        python-requests \
        python-six \
       "
