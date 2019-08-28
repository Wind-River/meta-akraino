DESCRIPTION = "Set of Jinja2 and ERB macros which help to encode Python and Ruby data structure into a different file format"
HOMEPAGE = "https://github.com/picotrading/config-encoder-macros"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=0cd5c34097456fdca9c171a05c3ea5f3"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "624ed05b65743a82bcfdef525176e6cfef5c71ee"
S = "${WORKDIR}/git"

inherit akraino-version

SRC_URI = "git://github.com/picotrading/config-encoder-macros.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

do_install() {
    install -m 0755 -d ${D}/opt/config-encoder-macros
    cp -r * ${D}/opt/config-encoder-macros
}

FILES_${PN} += "\
    /opt/config-encoder-macros \
"

RDEPENDS_${PN} = "bash"
