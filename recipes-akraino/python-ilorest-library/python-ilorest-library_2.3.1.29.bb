DESCRIPTION = "HPE RESTful API for iLO is a RESTful application programming interface for the management of iLO and iLO Chassis Manager based HPE servers."
HOMEPAGE = "https://github.com/HewlettPackard/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5b4a1b52d94480d1db38911e41241227"


FILESEXTRAPATHS_prepend := "${THISDIR}:"
_sha = "f6dae68800d20af859ed958c322108604d6a998a"
SRC_URI = "https://github.com/HewlettPackard/${PN}/archive/${_sha}.zip \
           file://0001-initial.patch \
"
SRC_URI[md5sum] = "94c7375707ef40393ec56f12d8245fe1"
SRC_URI[sha256sum] = "f26684e1a795fabcc7028ff58b7749d76dd220420f21022dd8b8f1ca0a02bb98"
S = "${WORKDIR}/${PN}-${_sha}"

_python_site_packages_path = "/usr/lib64/python2.7/site-packages/"

do_install_append() {
    python setup.py install --root ${D} --no-compile --install-purelib ${_python_site_packages_path} --install-scripts /usr/bin build_scripts --executable ${bindir}/python
}

FILES_${PN} += "\
    ${_python_site_packages_path} \
"

DEPENDS += " \
        python-pip \
        python-pbr-native \
        "

RDEPENDS_${PN} += " \
        bash \
        python \
        "
