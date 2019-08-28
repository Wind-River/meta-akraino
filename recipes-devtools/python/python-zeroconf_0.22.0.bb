DESCRIPTION = "This is fork of pyzeroconf, Multicast DNS Service Discovery for Python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=27818cd7fd83877a8e3ef82b82798ef4"

SRC_URI[md5sum] = "a660a0e57f12b259442de2ad7e6707df"
SRC_URI[sha256sum] = "fe66582c7b3ecc229ea4555b6d9da9bc26fc70134811e980b4fbd033e472b825"

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

PYPI_PACKAGE = "zeroconf"
inherit pypi setuptools
