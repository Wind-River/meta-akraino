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


RDEPENDS_packagegroup-stak-base += " \
    at \
    aufs-util \
    bzip2 \
    celt051 \
    ceph \
    crda \
    cronie \
    dhcp-client \
    diffutils \
    dpdk \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
    elfutils \
    ethtool \
    gzip \
    hdparm \
    hwloc \
    iproute2 \
    iptables \
    iputils \
    iw \
    ldd \
    lrzsz \
    lsof \
    lvm2 \
    man \
    man-pages \
    mdadm \
    minicom \
    msmtp \
    mtd-utils \
    ncurses-terminfo \
    ncurses-tools \
    net-tools \
    openssh \
    openssh-keygen \
    openssh-misc \
    openssh-scp \
    openssh-sftp-server \
    openssh-sftp \
    openssh-ssh \
    openssh-sshd \
    openvswitch \
    pam-plugin-wheel \
    postfix \
    ppp \
    quota \
    rfkill \
    rt-tests \
    schedtool-dl \
    sdparm \
    setserial \
    socat \
    spice \
    strace \
    tcf-agent \
    tcl \
    tunctl \
    tzdata \
    udev \
    udev-extraconf \
    unzip \
    usbutils \
    util-linux-fsck \
    vim \
    vim-common \
    vim-syntax \
    watchdog \
    which \
    wireless-regdb \
    zip \
    ${@bb.utils.contains('INCOMPATIBLE_LICENSE', 'GPLv3+', '', 'parted', d)} \
    ${@bb.utils.contains('INCOMPATIBLE_LICENSE', 'GPLv3+', '', 'screen', d)} \
"

RDEPENDS_packagegroup-stak-base_remove = " \
    kubernetes \
"

RDEPENDS_packagegroup-stak-perl += " \
    perl \
    perl-misc \
    perl-modules \
    perl-pod \
"

RDEPENDS_packagegroup-stak-python += " \
    python \
    python-misc \
    python-modules \
    python-ansible \
"
