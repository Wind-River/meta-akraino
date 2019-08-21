DESCRIPTION = "An image suitable for a Akraino host."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


require recipes-core/images/akraino-image-minimal.bb

IMAGE_INSTALL += " \
    kernel-modules \
    packagegroup-base-extended \
"

IMAGE_INSTALL += " \
    aufs-util \
    celt051 \
    ceph \
    dpdk \
    hwloc \
    libvirt \
    libvirt-libvirtd \
    libvirt-virsh \
    openvswitch \
    python-pyparsing \
    qemu \
    rt-tests \
    schedtool-dl \
    socat \
    spice \
    system-report \
    tunctl \
    udev \
    udev-extraconf \
"

IMAGE_FEATURES += " \
    nfs-server \
    package-management \
"
