DESCRIPTION = "An image suitable for a minimal Akraino guest or host."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


IMAGE_INSTALL = " \
   packagegroup-core-boot \
   packagegroup-vm \
"

inherit core-image
