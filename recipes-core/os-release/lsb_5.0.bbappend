
do_install_append () {
    # fake for akraino REC
    mv ${D}${sysconfdir}/lsb-release ${D}${sysconfdir}/lsb-release.d
}
