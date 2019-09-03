SUMMARY = "Device driver control utility"
DESCRIPTION = " \
    driverctl is a tool for manipulating and inspecting the system \
    device driver choices. \
 \
    Devices are normally assigned to their sole designated kernel driver \
    by default. However in some situations it may be desireable to \
    override that default, for example to try an older driver to \
    work around a regression in a driver or to try an experimental alternative \
    driver. Another common use-case is pass-through drivers and driver \
    stubs to allow userspace to drive the device, such as in case of \
    virtualization. \
 \
    driverctl integrates with udev to support overriding \
    driver selection for both cold- and hotplugged devices from the \
    moment of discovery, but can also change already assigned drivers, \
    assuming they are not in use by the system. The driver overrides \
    created by driverctl are persistent across system reboots \
    by default. \
"
HOMEPAGE = "https://gitlab.com/driverctl/driverctl"

LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "systemd"

SRC_URI = "https://gitlab.com/driverctl/${PN}/repository/archive.tar.gz?ref=${PV}#/${PN}-${PV}-${_commit}.tar.gz"
SRC_URI[md5sum] = "b06abc6124493f2958c8f519267b0f94"
SRC_URI[sha256sum] = "c3b75fbf4983a2e5ca8f1d02369e3337d4e78f9ffe10184039e79fce95733cec"


STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "3c668744f2242e453fdcd6dca36d3e1d82d0367a"
S = "${WORKDIR}/git"

SRC_URI = "git://gitlab.com/driverctl/driverctl.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

#EXTRA_OEMAKE = " \
#    LIBTOOL=${STAGING_BINDIR_CROSS}/${HOST_SYS}-libtool \
#    LIBPATH=${libdir} \
#    INCPATH=${includedir} \
#    -f makefile.shared \
#    "

do_install() {
	oe_runmake install DESTDIR=${D}
}

FILES_${PN} += " \
    /usr/share/bash-completion \
"

RDEPENDS_${PN} += " \
        coreutils \
        udev \
        bash \
"

pkg_postinst_${PN}() {
    udevadm control --reload-rules 
    udevadm trigger    
}

pkg_postrm_${PN} () {
    udevadm control --reload-rules 
    udevadm trigger    
}
