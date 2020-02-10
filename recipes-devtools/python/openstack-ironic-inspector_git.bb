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

DESCRIPTION = "an auxiliary service for discovering hardware properties for a node managed by `Ironic`_."
HOMEPAGE = "https://github.com/openstack/ironic-inspector"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS += " \
    python-pbr-native \
"

SRC_URI = "git://github.com/openstack/ironic-inspector.git;branch=stable/queens"

PV = "7.2.4+git${SRCPV}"
SRCREV = "17c796b49171b6133e988f78c92d7c9b7ed3fcf3"
S = "${WORKDIR}/git"

inherit setuptools

RDEPENDS_${PN} += " \
    python-alembic \
    python-automaton \
    python-babel \
    python-construct \
    python-eventlet \
    python-flask \
    python-futurist \
    python-ironic-lib \
    python-jsonpath-rw \
    python-jsonschema \
    python-keystoneauth1 \
    python-keystonemiddleware \
    python-netaddr \
    python-pbr \
    python-ironicclient \
    python-swiftclient \
    python-pytz \
    python-oslo.concurrency \
    python-oslo.config \
    python-oslo.context \
    python-oslo.db \
    python-oslo.i18n \
    python-oslo.log \
    python-oslo.middleware \
    python-oslo.policy \
    python-oslo.rootwrap \
    python-oslo.serialization \
    python-oslo.utils \
    python-retrying \
    python-six \
    python-stevedore \
    python-sqlalchemy \
"
