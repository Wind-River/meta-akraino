DESCRIPTION = "Python ServerView Common Command Interface (SCCI) Client Library"
HOMEPAGE = "https://github.com/openstack/scciclient"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

PV = "0.8.0+git${SRCPV}"
SRCREV = "d8f1f1c73bd95d21e540cb6e3acb75f5185de116"

SRCNAME = "scciclient"
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
        python-babel \
        python-pyghmi \
        python-pysnmp \
        python-requests \
        python-oslo.utils \
        python-oslo.serialization \
       "
