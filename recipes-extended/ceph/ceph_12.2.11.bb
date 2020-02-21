#
# Copyright (C) 2019 Wind River Systems, Inc.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

SUMMARY = "User space components of the Ceph file system"
LICENSE = "LGPLv2.1 & GPLv2 & Apache-2.0 & MIT"
LIC_FILES_CHKSUM = " \
    file://COPYING-LGPL2.1;md5=fbc093901857fcd118f065f900982c24 \
    file://COPYING-GPL2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://COPYING;md5=92d301c8fccd296f2221a68a8dd53828 \
"

DEPENDS = " \
    boost bzip2 curl expat gperf-native \
    keyutils libaio libibverbs lz4 \
    nspr nss \
    oath openldap openssl \
    python python-cython-native rocksdb snappy udev \
    valgrind xfsprogs zlib \
"

SRC_URI = " \
    http://download.ceph.com/tarballs/ceph-${PV}.tar.gz \
    file://0001-Correct-the-path-to-find-version.h-in-rocksdb.patch \
    file://0002-zstd-fix-error-for-cross-compile.patch \
    file://0003-ceph-add-pybind-support-in-OE.patch \
    file://0004-ceph-detect-init-correct-the-installation-for-OE.patch \
    file://0005-ceph-add-SNAPPY_ROOT_DIR-for-rocksdb.patch \
    file://0006-kv-RocksDBStore-extract-common-code-from-submit_tran.patch \
    file://0007-kv-RocksDBStore-update-to-accomodate-the-change-in-r.patch \
    file://0008-Replayer.cc-Explicitly-use-long-with-boost-posix_tim.patch \
    file://ceph.conf \
"
SRC_URI[md5sum] = "ad73a14ae12eb7d78e7eff2febede0cb"
SRC_URI[sha256sum] = "ca9a70600924303f7bae4dfa5197efc3379b30d6e70ce71e525204163ae637e9"

inherit cmake pythonnative python-dir systemd

SYSTEMD_SERVICE_${PN} = " \
    ceph-radosgw@.service \
    ceph-radosgw.target \
    ceph-mon@.service \
    ceph-mon.target \
    ceph-mds@.service \
    ceph-mds.target \
    ceph-disk@.service \
    ceph-osd@.service \
    ceph-osd.target \
    ceph.target \
    ceph-fuse@.service \
    ceph-fuse.target \
    ceph-rbd-mirror@.service \
    ceph-rbd-mirror.target \
    ceph-volume@.service \
    ceph-mgr@.service \
    ceph-mgr.target \
    rbdmap.service \
"

OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE = " \
    -DWITH_MANPAGE=OFF \
    -DWITH_FUSE=OFF \
    -DWITH_SPDK=OFF \
    -DWITH_LEVELDB=OFF \
    -DWITH_LTTNG=OFF \
    -DWITH_BABELTRACE=OFF \
    -DWITH_TESTS=OFF \
    -DWITH_MGR=OFF \
    -DWITH_MGR_DASHBOARD_FRONTEND=OFF \
    -DWITH_SYSTEM_BOOST=ON \
    -DWITH_SYSTEM_ROCKSDB=ON \
    -DWITH_SYSTEMD=ON \
    -DSNAPPY_ROOT_DIR=${RECIPE_SYSROOT}${prefix} \
"

do_configure_prepend () {
    echo "set( CMAKE_SYSROOT \"${RECIPE_SYSROOT}\" )" >> ${WORKDIR}/toolchain.cmake
    echo "set( CMAKE_DESTDIR \"${D}\" )" >> ${WORKDIR}/toolchain.cmake
    echo "set( PYTHON_SITEPACKAGES_DIR \"${PYTHON_SITEPACKAGES_DIR}\" )" >> ${WORKDIR}/toolchain.cmake
}

do_install_append () {
    sed -i -e 's:${WORKDIR}.*python2:${bindir}/python:' ${D}${bindir}/ceph
    sed -i -e 's:${WORKDIR}.*python2:${bindir}/python:' ${D}${bindir}/ceph-disk
    sed -i -e 's:${WORKDIR}.*python2:${bindir}/python:' ${D}${bindir}/ceph-detect-init
    sed -i -e 's:${WORKDIR}.*python2:${bindir}/python:' ${D}${bindir}/ceph-volume
    sed -i -e 's:${WORKDIR}.*python2:${bindir}/python:' ${D}${bindir}/ceph-volume-systemd

    find ${D} -name SOURCES.txt | xargs sed -i -e 's:${WORKDIR}::'
    install -d ${D}${sysconfdir}/ceph
    install -m 644 ${WORKDIR}/ceph.conf ${D}${sysconfdir}/ceph/

    mv ${D}${libexecdir}/ceph/ceph-osd-prestart.sh ${D}${libdir}/ceph
    mv ${D}${libexecdir}/ceph/ceph_common.sh ${D}${libdir}/ceph

    install -d ${D}${systemd_unitdir}/
    mv ${D}${libexecdir}/systemd/system ${D}${systemd_unitdir}/

    # we have dropped sysvinit bits
    rm -f ${D}/${sysconfdir}/init.d/ceph

    install -m 0644 -D ${S}/src/etc-rbdmap ${D}${sysconfdir}/ceph/rbdmap
    install -m 0644 -D ${S}/etc/sysconfig/ceph ${D}${sysconfdir}/sysconfig/ceph
    install -m 0644 -D ${S}/systemd/ceph.tmpfiles.d ${D}${sysconfdir}/tmpfiles.d/ceph-common.conf
    install -m 0755 -D ${S}/systemd/ceph ${D}${sbindir}/rcceph
    install -m 0644 -D ${S}/systemd/50-ceph.preset ${D}${systemd_unitdir}/system-preset/50-ceph.preset

    install -m 0644 -D ${S}/src/logrotate.conf ${D}${sysconfdir}/logrotate.d/ceph

    install -m 0644 -D ${S}/etc/sysctl/90-ceph-osd.conf ${D}${exec_prefix}/lib/sysctl.d/90-ceph-osd.conf
    
    # udev rules
    install -m 0644 -D ${S}/udev/50-rbd.rules ${D}${nonarch_base_libdir}/udev/rules.d/50-rbd.rules
    install -m 0644 -D ${S}/udev/60-ceph-by-parttypeuuid.rules ${D}${nonarch_base_libdir}/udev/rules.d/60-ceph-by-parttypeuuid.rules
    install -m 0644 -D ${S}/udev/95-ceph-osd.rules ${D}${nonarch_base_libdir}/udev/rules.d/95-ceph-osd.rules
    
    # set up placeholder directories
    mkdir -p ${D}${localstatedir}/log/ceph
    mkdir -p ${D}${localstatedir}/lib/ceph/tmp
    mkdir -p ${D}${localstatedir}/lib/ceph/mon
    mkdir -p ${D}${localstatedir}/lib/ceph/osd
    mkdir -p ${D}${localstatedir}/lib/ceph/mds
    mkdir -p ${D}${localstatedir}/lib/ceph/mgr
    mkdir -p ${D}${localstatedir}/lib/ceph/radosgw
    mkdir -p ${D}${localstatedir}/lib/ceph/bootstrap-osd
    mkdir -p ${D}${localstatedir}/lib/ceph/bootstrap-mds
    mkdir -p ${D}${localstatedir}/lib/ceph/bootstrap-rgw
    mkdir -p ${D}${localstatedir}/lib/ceph/bootstrap-mgr
    mkdir -p ${D}${localstatedir}/lib/ceph/bootstrap-rbd
}

PACKAGES += " \
    ${PN}-python \
"

FILES_${PN} += "\
    ${libdir}/rados-classes/*.so.* \
    ${libdir}/ceph/compressor/*.so \
    ${libdir}/rados-classes/*.so \
    ${libdir}/ceph/*.so \
    ${exec_prefix}/lib/sysctl.d \
    ${systemd_unitdir}/system-preset \
"

FILES_${PN}-python = "\
    ${PYTHON_SITEPACKAGES_DIR}/* \
"

RDEPENDS_${PN} += "\
    bash \
    gptfdisk \
    python \
    python-misc \
    python-modules \
    python-prettytable \
    ${PN}-python \
"

COMPATIBLE_HOST = "(x86_64).*"

INSANE_SKIP_${PN}-python += "ldflags"
INSANE_SKIP_${PN} += "dev-so"
