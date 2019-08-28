DESCRIPTION = "This is fork of pyzeroconf, Multicast DNS Service Discovery for Python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b0a8ffe0d25e797b355d0f5de9e61baa"

SRC_URI[md5sum] = "48fc50ada1ceb558ff460d6e5f7520ab"
SRC_URI[sha256sum] = "557f1f1a8c7a147da19d7004f10cc778a02ee48ab6f4e50b2a83ed49b25706c5"

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} = "python bash"

PYPI_PACKAGE = "UcsSdk"

inherit pypi setuptools

