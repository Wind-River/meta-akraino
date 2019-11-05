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

DESCRIPTION = "Set of Jinja2 and ERB macros which help to encode Python and Ruby data structure into a different file format"
HOMEPAGE = "https://github.com/picotrading/config-encoder-macros"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=0cd5c34097456fdca9c171a05c3ea5f3"

STABLE = "master"
PROTOCOL = "https"
BRANCH = "master"
SRCREV = "624ed05b65743a82bcfdef525176e6cfef5c71ee"
S = "${WORKDIR}/git"

inherit akraino-version

SRC_URI = "git://github.com/picotrading/config-encoder-macros.git;protocol=${PROTOCOL};rev=${SRCREV};branch=${BRANCH}"

do_install() {
    install -m 0755 -d ${D}/opt/config-encoder-macros
    cp -r * ${D}/opt/config-encoder-macros
}

FILES_${PN} += "\
    /opt/config-encoder-macros \
"

RDEPENDS_${PN} = "bash"
