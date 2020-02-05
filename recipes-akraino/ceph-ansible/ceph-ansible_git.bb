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

SUMMARY = "Ansible playbooks for Ceph"
DESCRIPTION = "Ansible playbooks for Ceph, the distributed filesystem"
HOMEPAGE = "https://github.com/ceph/ceph-ansible"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cee9873ed5a568f7cff75afd338878cb"

SRC_URI = "git://github.com/ceph/${BPN}.git;branch=stable-3.0"
SRCREV = "cdda001c637f8b54508a1ceded4a008ca42e0357"

PV = "3.0.24+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += " \
    python-ansible \
"

do_compile () {
    :
}

do_install () {
    install -d ${D}${datadir}/ceph-ansible
    for f in ansible.cfg *.yml *.sample group_vars roles library plugins infrastructure-playbooks; do
        cp -av --no-preserve=ownership ${f} ${D}${datadir}/ceph-ansible
    done


    # Strip coreos files.
    # These are unneeded on RPM-based distros, and the playbooks download random
    # things from around the internet.
    cd ${D}${datadir}/ceph-ansible
    rm -r roles/ceph-common-coreos
    rm group_vars/common-coreoss.yml.sample

    # These untested playbooks are too unstable for users.
    rm -r infrastructure-playbooks/untested-by-ci
}

RDEPENDS_${PN} += " \
    python-ansible \
    python-netaddr \
"
