
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
    openssh-scp \
    openssh-sftp-server \
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
