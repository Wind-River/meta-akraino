DESCRIPTION = "This is fork of pyzeroconf, Multicast DNS Service Discovery for Python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b8eec2f0885ebe1362d0bdb1617f61b5"

SRC_URI[md5sum] = "e7b301eddd703ab74a48c59a8fda1f97"
SRC_URI[sha256sum] = "510a414b270986fba3c79cb76d90a4c910c701bfb43ff983a5d4e92846050e17"

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

PYPI_PACKAGE = "pysendfile"
inherit pypi setuptools
