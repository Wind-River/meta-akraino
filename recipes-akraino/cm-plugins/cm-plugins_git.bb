DESCRIPTION = "Plugins for configuration manager"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "b3603a371a729a17e5130216fc71d67b6f0827e8"
S = "${WORKDIR}/git"

LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://gerrit.akraino.org/r/ta/cm-plugins.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

inherit akraino-version

DEPENDS += " \
	"

require activators.inc
require inventoryhandlers.inc
require recuserconfighandlers.inc
require userconfighandlers.inc
require validators.inc


do_configure () {
	:
} 

do_compile() {
	:	
}

do_install () {
	:
}

pkg_postinst_ontarget_${PN} () {
}

#FILES_${PN} = " "
#FILES_${PN}-dev += " \
#	var/lib/sm/watchdog/modules/libsm_watchdog_nfs.so \
#	"
