
do_install_append () {
    rm ${D}${sysconfdir}/os-release
    # fake for akraino REC
    echo "CentOS Linux release 7.6.1810 (Core)" > ${D}${sysconfdir}/redhat-release
}
