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

SUMMARY ="uWSGI application server container"
DESCRIPTION = "An unladen web framework for building APIs and app backends."
HOMEPAGE = "http://projects.unbit.it/uwsgi/"
SECTION = "net"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=33ab1ce13e2312dddfad07f97f66321f"

SRCNAME = "uwsgi"
SRC_URI = "git://github.com/unbit/uwsgi.git;branch=uwsgi-2.0"

SRCREV = "4b3e9621c0a3eb2aacf937b8e84e67b23b20490b"

PV="2.0.17.1+git${SRCPV}"

S = "${WORKDIR}/git"

inherit setuptools pkgconfig

CFLAGS += " -Wno-cast-function-type"
# prevent host contamination and remove local search paths
export UWSGI_REMOVE_INCLUDES = "/usr/include,/usr/local/include"

DEPENDS += " \
    e2fsprogs \
    python-pip \
    python-six \
    yajl \
"
