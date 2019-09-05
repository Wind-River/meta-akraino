SUMMARY = "Containers as a Service Custom Metrics component"
DESCRIPTION = "This contains the custom metrics container image for the CaaS subsystem."
HOMEPAGE = "https://github.com/DirectXMan12/k8s-prometheus-adapter"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ff253ad767462c46be284da12dda33e8"

PROTOCOL = "https"
SRCREV = "a65d286d5ee14c4c5bf47112cb341d849fd40644"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-metrics;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "0.5.0"
MINOR_VERSION = "1"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "custom_metrics"
DOCKER_EXTRA_ARG += "--build-arg CUSTOM_METRICS=${MAJOR_VERSION}"
