DESCRIPTION = "Library for managing machines with Dell iDRAC cards.."
HOMEPAGE = "http://bitbucket.org/zzzeek/alembic"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fa818a259cbed7ce8bc2a22d35a464fc"

SRCNAME = "python-dracclient"

SRC_URI[md5sum] = "58071f6aa0981117a35157859e4a678f"
SRC_URI[sha256sum] = "0d99da3226ff7ca89cc1c953d5af00e2d045e34abe074b20020f2492c8ab8477"

SRC_URI = "git://github.com/openstack/${SRCNAME}.git; \
    "
SRCREV = "64ce3e24249c47edb17ff41d1cbd7f68145c639d"
PV = "3.0.0+git${SRCPV}"
S = "${WORKDIR}/git"


inherit setuptools

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

#RDEPENDS_${PN} += " \
#    python-sqlalchemy \
#    python-mako \
#    python-python-editor \
#    "
