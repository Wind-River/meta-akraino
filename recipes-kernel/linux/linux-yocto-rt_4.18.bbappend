FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

KBRANCH_intel-x86  = "v4.18/standard/preempt-rt/intel-x86"
KBRANCH_intel-x86-common  = "v4.18/standard/preempt-rt/intel-x86"

SRC_URI_append = " \
    file://hpsa.cfg \
    file://other_drivers.cfg \
    file://docker_kube.cfg \
    file://akraino-host-rt-tune.cfg \
    file://0001-timers-Don-t-wake-ktimersoftd-on-every-tick.patch \
    file://0002-timers-Don-t-search-for-expired-timers-while-TIMER_S.patch \
"

KERNEL_FEATURES_append += " \
    features/kvm/qemu-kvm-enable.scc \
    features/netfilter/netfilter.scc \
    features/intel-dpdk/intel-dpdk.scc \
    features/cgroups/cgroups.scc \
"