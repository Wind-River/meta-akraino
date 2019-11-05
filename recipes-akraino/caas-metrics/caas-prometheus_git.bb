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

SUMMARY = "Containers as a Service Prometheus component"
DESCRIPTION = "This contains the prometheus container image for the CaaS subsystem."
HOMEPAGE = "https://github.com/prometheus/prometheus"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ff253ad767462c46be284da12dda33e8"

PROTOCOL = "https"
SRCREV = "a65d286d5ee14c4c5bf47112cb341d849fd40644"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-metrics;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "2.11.1"
MINOR_VERSION = "0"
GO_VERSION = "1.12.1"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "prometheus"
DOCKER_EXTRA_ARG += " \
    --build-arg PROMETHEUS=${MAJOR_VERSION} \
    --build-arg GO_REQUIRED=${GO_VERSION} \
"
