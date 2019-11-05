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
    cfg/rbd.scc \
    cfg/virtio.scc \
    features/aufs/aufs-enable.scc \
    features/cgroups/cgroups.scc \
    features/intel-dpdk/intel-dpdk.scc \
    features/intel-dpdk/intel-dpdk.scc \
    features/kvm/qemu-kvm-enable.scc \
    features/lxc/lxc-enable.scc \
    features/netfilter/netfilter.scc \
    features/netfilter/netfilter.scc \
    features/nfsd/nfsd-enable.scc \
    features/vfio/vfio.scc \
    features/xfs/xfs.scc \
"
