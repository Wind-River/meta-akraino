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

SUMMARY = "CaaS Logging restful API and CLI plugins"
DESCRIPTION = "This RPM contains the Elasticsearch container image for CaaS subsystem."
HOMEPAGE = "https://gerrit.akraino.org/r/ta/caas-logging"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

PROTOCOL = "https"
SRCREV = "dbb52626787b945265e4dbfaee8a2905fa410b67"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-logging;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

DEPENDS = "rsync-native python-setuptools-native"

inherit akraino-version distutils-base

platform_bin_path = "/usr/local/bin"
native_python_bin = "${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN}"


do_compile () {
    :
}

do_install () {
    mkdir -p ${D}${PYTHON_SITEPACKAGES_DIR}/caas_logging

    mkdir -p ${D}${PYTHON_SITEPACKAGES_DIR}/yarf/handlers/caas_logging
    rsync -rlpD ${S}/src/caas_logging/rest-plugin/* ${D}/${PYTHON_SITEPACKAGES_DIR}/yarf/handlers/caas_logging

    mkdir -p ${D}/opt/cmframework/activators/
    rsync -rlpD ${S}/src/caas_logging/activator/* ${D}/opt/cmframework/activators

    cd ${S}/src
    ${native_python_bin} setup.py install --root ${D} --no-compile \
        --install-purelib ${PYTHON_SITEPACKAGES_DIR} --install-scripts ${platform_bin_path}
    cd -
}

FILES_${PN} = "\
    ${PYTHON_SITEPACKAGES_DIR} \
    /opt \
"
