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

SUMMARY = "Containers as a Service flannel component"
DESCRIPTION = "This contains the sriov network device plugin container for CaaS subsystem."
HOMEPAGE = "https://github.com/intel/sriov-network-device-plugin"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

PROTOCOL = "https"
SRCREV = "a7fdbdf37816d2beced1bbe1cd6f2b24c207980f"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-danm;protocol=${PROTOCOL}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "3.0.0"
MINOR_VERSION = "0"
SRIOVDP_HASH = "a015e56ae715e2b6dae15e42827e4e8f43eeceac"
go_version = "1.12.9"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "sriovdp"
DOCKER_EXTRA_ARG += " \
    --build-arg SRIOVDP=${MAJOR_VERSION} \
    --build-arg SRIOVDP_HASH=${SRIOVDP_HASH} \
    --build-arg go_version=${go_version} \
"
