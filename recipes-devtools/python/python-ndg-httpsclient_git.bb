DESCRIPTION = "HTTPS client implementation for httplib and urllib2"
HOMEPAGE = "https://github.com/cedadev/ndg_httpsclient"
SECTION = "devel/python"
LICENSE = "STFC"
LIC_FILES_CHKSUM = "file://ndg/httpsclient/LICENSE;md5=f6f6af2edb690eaeee9a16558cafbada"

SRC_URI[md5sum] = "bf962f06bddb9ad84afabcf2dfe893b5"
SRC_URI[sha256sum] = "25235c2f67cd97ba385d0103076fa7e0f322a6cb01e490e7d296279243126c55"

SRCNAME = "ndg_httpsclient"

SRC_URI[md5sum] = "58071f6aa0981117a35157859e4a678f"
SRC_URI[sha256sum] = "0d99da3226ff7ca89cc1c953d5af00e2d045e34abe074b20020f2492c8ab8477"

SRC_URI = "git://github.com/cedadev/${SRCNAME}.git; \
    "

SRCREV = "af9fed2c22e283c745135a8d745e36b8540bb8cc"
PV = "0.5.1+git${SRCPV}"
S = "${WORKDIR}/git"


inherit setuptools
DEPENDS_${PN} += " \
    python-pip \
    python-native \
    "

RDEPENDS_${PN} += " \
    python-pyopenssl \
    python-pyasn1 \
    "
