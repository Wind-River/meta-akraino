SUMMARY = "Containers as a Service flannel component"
DESCRIPTION = "This contains the sriov network device plugin container for CaaS subsystem."
HOMEPAGE = "https://github.com/intel/sriov-network-device-plugin"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

PROTOCOL = "https"
SRCREV = "31349430c54e4ce1f649376614e4b09738954d5d"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-danm;protocol=${PROTOCOL}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "2.0.0"
MINOR_VERSION = "4"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "sriovdp"
DOCKER_EXTRA_ARG += "--build-arg SRIOVDP=${MAJOR_VERSION}"
