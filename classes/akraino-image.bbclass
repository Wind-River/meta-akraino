inherit core-image

WRLINUX_IMAGE_CLASSES = " core-image \
	${@bb.utils.contains('DISTRO_FEATURES', 'ostree', 'flux-ota', '', d)} \
"
inherit ${WRLINUX_IMAGE_CLASSES}


# ensure we have password and group files before we do_rootfs
check_for_passwd_group() {
    if [ ! -f $D/${sysconfdir}/passwd -o ! -f $D/${sysconfdir}/group ]; then
        bberror "Nothing provides ${sysconfdir}/passwd and/or ${sysconfdir}/group"  
	exit 1
    fi
}

ROOTFS_POSTPROCESS_COMMAND_prepend = "check_for_passwd_group ; "

WRPRELIMPKGDATADIR ?= "${TMPDIR}/pkgdata-prelim"
WRPKGDATADIR ?= "${TMPDIR}/pkgdata"

python do_image_info() {
    prelim_dirs = d.getVar('WRPRELIMPKGDATADIR', True) or ""
    built_dirs  = d.getVar('WRPKGDATADIR', True) or ""

    package_install = d.getVar('PACKAGE_INSTALL', True) or ""
    package_install_attemptonly = d.getVar('PACKAGE_INSTALL_ATTEMPTONLY', True) or ""

    globs = d.getVar('IMAGE_INSTALL_COMPLEMENTARY', True) or ""
    for translation in d.getVar('IMAGE_LINGUAS', True).split():
        translated = translation.split('-')[0]
        globs += ' *-locale-%s' % translated
        if translated != translation:
            globs += ' *-locale-%s' % translation

    bb.plain('PKGDATA dirs: %s %s' % (prelim_dirs, built_dirs))
    bb.plain('INSTALL: %s' % package_install)
    bb.plain('INSTALL ATTEMPT: %s' % package_install_attemptonly)
    bb.plain('GLOBS: %s' % globs)
}

addtask image_info
do_image_info[nostamp] = "1"
