#!/bin/sh

SCRIPTS_DIR=`dirname $0`
SCRIPTS_DIR=`readlink -f $SCRIPTS_DIR`

WORKSPACE=`readlink -f $1`

SRC_WRL_DIR=${WORKSPACE}/src_wrl1018
SRC_EXTRA_DIR=${WORKSPACE}/src_extra_layers
PRJ_BUILD_DIR=${WORKSPACE}/prj_wrl1018_akraino

echo_info () {
    echo "INFO: $1"
}

echo_cmd () {
    echo
    echo "$1"
    echo "CMD: ${RUN_CMD}"
}

mkdir -p ${SRC_WRL_DIR} ${PRJ_BUILD_DIR} ${SRC_EXTRA_DIR}

echo_info "The following directories are created in your workspace: ${WORKSPACE}"
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
for i in "git clone https://github.com/jackiehjm/meta-akraino.git" \
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
cat ${SCRIPTS_DIR}/extra_local.conf >> conf/local.conf

# Build the Akraino image
mkdir -p logs
TIMESTAMP=`date +"%Y%m%d_%H%M%S"`
bitbake akraino-image-rec 2>&1|tee logs/bitbake_akraino-image-rec_${TIMESTAMP}.log
