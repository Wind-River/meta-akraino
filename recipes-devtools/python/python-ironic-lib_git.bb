DESCRIPTION = "A common library to be used exclusively by projects under the Ironic governance."
HOMEPAGE = "https://github.com/openstack/ironic-lib"
SECTION = "devel/python"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d2794c0df5b907fdace235a619d80314"

PV = "2.16.3+git${SRCPV}"
SRCREV = "eca4ac942a13f87d8ca836e0487595a52d872390"

SRCNAME = "ironic-lib"
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
        python-oslo.concurrency \
        python-oslo.config \
        python-oslo.i18n \
        python-oslo.serialization \
        python-oslo.service \
        python-oslo.utils \
        python-oslo.log \
        python-six \
        python-zeroconf \
        python-requests \
       "
