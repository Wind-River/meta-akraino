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

inherit live-vm-common

do_akraino_iso[depends] += " \
    dosfstools-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot \
    cdrtools-native:do_populate_sysroot \
    virtual/kernel:do_deploy \
    ${MLPREFIX}syslinux:do_populate_sysroot \
    syslinux-native:do_populate_sysroot \
"


LABELS_LIVE ?= "install boot"
ROOT_LIVE = "root=/dev/ram0"
INITRD_IMAGE_LIVE ?= "${MLPREFIX}core-image-minimal-initramfs"
INITRD_LIVE = "${DEPLOY_DIR_IMAGE}/${INITRD_IMAGE_LIVE}-${MACHINE}.cpio.gz"

AKRAINO_ROOTFS_TYPE ?= "wic.qcow2"
AKRAINO_ROOTFS ?= "${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.${AKRAINO_ROOTFS_TYPE}"

ISODIR = "${S}/akraino_iso"
EFIIMGDIR = "${S}/efi_img"
COMPACT_ISODIR = "${S}/iso.z"

ISOLINUXDIR ?= "/isolinux"
ISO_BOOTIMG = "isolinux/isolinux.bin"
ISO_BOOTCAT = "isolinux/boot.cat"
MKISOFS_OPTIONS = "-no-emul-boot -boot-load-size 4 -boot-info-table"

BOOTIMG_VOLUME_ID   ?= "boot"
BOOTIMG_EXTRA_SPACE ?= "512"

populate_akraino_live() {
	populate_kernel $1
	if [ -s "${AKRAINO_ROOTFS}" ]; then
		install -m 0644 ${AKRAINO_ROOTFS} $1/rootfs.img
	fi
}

build_akraino_iso() {
	# Only create an ISO if we have an INITRD and the live or iso image type was selected
	if [ -z "${INITRD}" ] || [ "${@bb.utils.contains_any('IMAGE_FSTYPES', 'live iso', '1', '0', d)}" != "1" ]; then
		bbnote "ISO image will not be created."
		return
	fi
	# ${INITRD} is a list of multiple filesystem images
	for fs in ${INITRD}
	do
		if [ ! -s "$fs" ]; then
			bbwarn "ISO image will not be created. $fs is invalid."
			return
		fi
	done

	populate_akraino_live ${ISODIR}

	if [ "${PCBIOS}" = "1" ]; then
		syslinux_iso_populate ${ISODIR}
	fi
	if [ "${EFI}" = "1" ]; then
		efi_iso_populate ${ISODIR}
		build_fat_img ${EFIIMGDIR} ${ISODIR}/efi.img
	fi

	# EFI only
	if [ "${PCBIOS}" != "1" ] && [ "${EFI}" = "1" ] ; then
		# Work around bug in isohybrid where it requires isolinux.bin
		# In the boot catalog, even though it is not used
		mkdir -p ${ISODIR}/${ISOLINUXDIR}
		install -m 0644 ${STAGING_DATADIR}/syslinux/isolinux.bin ${ISODIR}${ISOLINUXDIR}
	fi

	# We used to have support for zisofs; this is a relic of that
	mkisofs_compress_opts="-r"

	# Check the size of ${ISODIR}/rootfs.img, use mkisofs -iso-level 3
	# when it exceeds 3.8GB, the specification is 4G - 1 bytes, we need
	# leave a few space for other files.
	mkisofs_iso_level=""

        if [ -n "${AKRAINO_ROOTFS}" ] && [ -s "${AKRAINO_ROOTFS}" ]; then
		rootfs_img_size=`stat -c '%s' ${ISODIR}/rootfs.img`
		# 4080218931 = 3.8 * 1024 * 1024 * 1024
		if [ $rootfs_img_size -gt 4080218931 ]; then
			bbnote "${ISODIR}/rootfs.img execeeds 3.8GB, using '-iso-level 3' for mkisofs"
			mkisofs_iso_level="-iso-level 3"
		fi
	fi

	if [ "${PCBIOS}" = "1" ] && [ "${EFI}" != "1" ] ; then
		# PCBIOS only media
		mkisofs -V ${BOOTIMG_VOLUME_ID} \
		        -o ${IMGDEPLOYDIR}/${IMAGE_NAME}.iso \
			-b ${ISO_BOOTIMG} -c ${ISO_BOOTCAT} \
			$mkisofs_compress_opts \
			${MKISOFS_OPTIONS} $mkisofs_iso_level ${ISODIR}
	else
		# EFI only OR EFI+PCBIOS
		mkisofs -A ${BOOTIMG_VOLUME_ID} -V ${BOOTIMG_VOLUME_ID} \
		        -o ${IMGDEPLOYDIR}/${IMAGE_NAME}.iso \
			-b ${ISO_BOOTIMG} -c ${ISO_BOOTCAT} \
			$mkisofs_compress_opts ${MKISOFS_OPTIONS} $mkisofs_iso_level \
			-eltorito-alt-boot -eltorito-platform efi \
			-b efi.img -no-emul-boot \
			${ISODIR}
		isohybrid_args="-u"
	fi

	isohybrid $isohybrid_args ${IMGDEPLOYDIR}/${IMAGE_NAME}.iso
}

python do_akraino_iso() {
    set_live_vm_vars(d, 'LIVE')
    if d.getVar("PCBIOS") == "1":
        bb.build.exec_func('build_syslinux_cfg', d)
    if d.getVar("EFI") == "1":
        bb.build.exec_func('build_efi_cfg', d)
    bb.build.exec_func('build_akraino_iso', d)
    bb.build.exec_func('create_symlinks', d)
}

addtask akraino_iso before do_image_complete after do_image_wic

