DESCRIPTION = "A database migration tool for SQLAlchemy."
HOMEPAGE = "http://bitbucket.org/zzzeek/alembic"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d07407716fd24408b5747b0fa2262775"

SRC_URI[md5sum] = "21b344a70ce637699c18bf074a080649"
SRC_URI[sha256sum] = "0e3b50e96218283ec7443fb661199f5a81f5879f766967a8a2d25e8f9d4e7919"

inherit setuptools pypi

RDEPENDS_${PN} += " \
    python-sqlalchemy \
    python-mako \
    python-python-editor \
    "
