#!/bin/sh
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

help_info () {
cat << ENDHELP
Usage:
$(basename $0) WORKSPACE_DIR
where:
    WORKSPACE_DIR is the path for the project
ENDHELP
}

echo_info () {
    echo "INFO: $1"
}

echo_error () {
    echo "ERROR: $1"
}

echo_cmd () {
    echo
    echo "$1"
    echo "CMD: ${RUN_CMD}"
}

check_docker () {
    echo_info "Verify if you can run docker commands without sudo."
    docker run hello-world
    if [ $? -ne 0 ]; then
        echo_error "You can't run docker commands without sudo!"
        exit 1
    fi
}

if [ $# -eq 0 ]; then
    help_info
    exit
fi

SCRIPTS_DIR=`dirname $0`
SCRIPTS_DIR=`readlink -f $SCRIPTS_DIR`

WORKSPACE=`readlink -f $1`

SRC_WRL_DIR=${WORKSPACE}/src_wrl1018
SRC_EXTRA_DIR=${WORKSPACE}/src_extra_layers
PRJ_BUILD_DIR=${WORKSPACE}/prj_wrl1018_akraino

mkdir -p ${SRC_WRL_DIR} ${PRJ_BUILD_DIR} ${SRC_EXTRA_DIR}

echo_info "The following directories are created in your workspace(${WORKSPACE}):"
echo_info "For wrlinux1018 source: ${SRC_WRL_DIR}"
echo_info "For extra layers source: ${SRC_EXTRA_DIR}"
echo_info "For build project: ${PRJ_BUILD_DIR}"

# Clone the source of WRLinux BASE 10.18 from github and setup
RUN_CMD="git clone --branch WRLINUX_10_18_BASE git://github.com/WindRiver-Labs/wrlinux-x.git"
echo_cmd "Cloning wrlinux 1018 source from github:"
cd ${SRC_WRL_DIR}
${RUN_CMD}

RUN_CMD="./wrlinux-x/setup.sh --machines intel-x86-64"
echo_cmd "Setup wrlinux build project:"
${RUN_CMD}

# Clone extra layers
echo_info "Cloning extra layers:"

cd ${SRC_EXTRA_DIR}
for i in "git clone https://github.com/Wind-River/meta-akraino.git" \
         "git clone --branch thud git://github.com/rauc/meta-rauc.git" \
         "git clone --branch thud git://git.yoctoproject.org/meta-security" \
         "git clone --branch WRLINUX_10_18_BASE_akraino https://github.com/jackiehjm/meta-starlingX.git" \
         "git clone --branch WRLINUX_10_18_BASE_akraino git://github.com/jackiehjm/meta-cloud-services.git"; do
    RUN_CMD="${i}"
    echo_cmd "Cloing with:"
    ${RUN_CMD}
done

# Source the build env
cd ${SRC_WRL_DIR}
. ./environment-setup-x86_64-wrlinuxsdk-linux
set ${PRJ_BUILD_DIR}
. ./oe-init-build-env ${PRJ_BUILD_DIR}

# Add the meta-akraino layer and required layers
cd ${PRJ_BUILD_DIR}
bitbake-layers add-layer ${SRC_EXTRA_DIR}/meta-akraino
bitbake-layers add-layer ${SRC_EXTRA_DIR}/meta-starlingX
bitbake-layers add-layer ${SRC_EXTRA_DIR}/meta-rauc
bitbake-layers add-layer ${SRC_EXTRA_DIR}/meta-security
bitbake-layers add-layer ${SRC_EXTRA_DIR}/meta-security/meta-tpm
bitbake-layers add-layer ${SRC_EXTRA_DIR}/meta-security/meta-security-compliance
bitbake-layers add-layer ${SRC_EXTRA_DIR}/meta-cloud-services
bitbake-layers add-layer ${SRC_EXTRA_DIR}/meta-cloud-services/meta-openstack


# Add extra configs into local.conf
docker_bin=`which docker`
cat << EOF >> conf/local.conf
#######################
# Configs for Akraino #
#######################
DISTRO = "akraino"
BB_NO_NETWORK = '0'
docker_bin = "${docker_bin}"

# For container image
WR_APP_CONTAINER_APP = "rt-tests"
EOF

mkdir -p logs
TIMESTAMP=`date +"%Y%m%d_%H%M%S"`

# Build the Akraino host image
bitbake akraino-image-rec 2>&1|tee logs/bitbake_akraino-image-rec_${TIMESTAMP}.log

# Build the container image
bitbake wr-app-container 2>&1|tee logs/bitbake_wr-app-container_${TIMESTAMP}.log
