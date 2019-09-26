DESCRIPTION = "An image suitable for Akraino REC."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit akraino-image

IMAGE_INSTALL += " \
    kernel-modules \
    packagegroup-akraino-base \
    packagegroup-akraino-docker \
    packagegroup-base-extended \
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    packagegroup-self-hosted \
    packagegroup-stak-python \
    packagegroup-stak-base \
    packagegroup-stak-ruby \
    packagegroup-stak-puppet \
    packagegroup-vm \
"

IMAGE_FEATURES += " \
    nfs-server \
    package-management \
"
