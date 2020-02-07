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

SUMMARY = "Product build information"
DESCRIPTION = "Product build information"
HOMEPAGE = "https://gerrit.akraino.org/r/admin/repos/ta/manifest"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PROTOCOL = "https"
SRCREV = "a132cb7482f28cada3bd545e35c7031f91f3c1d1"

SRC_URI = "git://gerrit.akraino.org/r/ta/manifest;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}${sysconfdir}
    cat > ${D}${sysconfdir}/product-release << EOF
release=${REC_RELEASE}
build=${REC_BUILD}
EOF
}
