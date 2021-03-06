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

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://init-install-efi.sh-fix-rootfs.img-not-found-issue.patch;apply=no \
            file://init-install-efi.sh-copy-the-guest-image.img-for-REC-dep.patch;apply=no \
"

do_install_append () {
	patch ${D}/install-efi.sh ${WORKDIR}/init-install-efi.sh-fix-rootfs.img-not-found-issue.patch
	patch ${D}/install-efi.sh ${WORKDIR}/init-install-efi.sh-copy-the-guest-image.img-for-REC-dep.patch
}
