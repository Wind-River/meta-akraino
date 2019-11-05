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

DESCRIPTION = "This role enables users to install and configure ntp on their hosts."
HOMEPAGE = "https://github.com/resmo/ansible-role-ntp"

LICENSE = "Apache-2.0 & BSD"
LIC_FILES_CHKSUM = "file://README.md;beginline=30;endline=34;md5=cee7231782f405a1f9c822eed79f01f4"

SRCREV = "4fc673cfdf6cb3704a8be10cd10728a21be8f1bb"

SRC_URI = "git://github.com/resmo/ansible-role-ntp;rev=${SRCREV} \
           file://0001-initial.patch \
"

S = "${WORKDIR}/git"

PV = "0.4.0+git${SRCREV}"

RDEPENS_${PN} = "python-ansible"

do_install_append() {
	cd ${S}
	install -d ${D}${sysconfdir}/ansible/roles/ntp

	tar --exclude='.travis.yml' \
	    --exclude='.gitignore' \
	    --exclude='.pc' \
	    --exclude='README.md' \
	    --exclude='*tests*' \
	    --exclude='.git' \
	    --exclude='.gitreview' \
	    --no-same-owner -cpf - . \
	    | tar --no-same-owner -xpf - \
	          -C ${D}${sysconfdir}/ansible/roles/ntp
}
