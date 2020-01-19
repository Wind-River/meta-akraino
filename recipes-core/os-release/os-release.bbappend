
do_install_append () {
    # fake for akraino REC
    echo "CentOS Linux release 7.6.1810 (Core)" > ${D}${sysconfdir}/redhat-release
}
