DESCRIPTION = "Python ServerView Common Command Interface (SCCI) Client Library"
HOMEPAGE = "https://github.com/openstack/scciclient"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PV = "3.5.0+git${SRCPV}"
SRCREV = "9bb11505d79c97e28b32c821dda0e47ef2ef1dc7"

SRCNAME = "ironic-inspector-client"
SRC_URI = "git://github.com/openstack/python-${SRCNAME}.git;branch=stable/stein"

S = "${WORKDIR}/git"

inherit setuptools

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

# RDEPENDS_default: 
RDEPENDS_${PN} += " \
        python-pbr \
        python-keystoneauth1 \
        python-oslo.i18n \
        python-pyyaml \
        python-osc-lib \
        python-requests \
        python-oslo.utils \
       "
