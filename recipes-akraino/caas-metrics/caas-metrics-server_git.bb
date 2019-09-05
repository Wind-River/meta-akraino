SUMMARY = "Containers as a Service Metrics Server component"
DESCRIPTION = "This contains the metrics server container image for the CaaS subsystem."
HOMEPAGE = "https://github.com/kubernetes-incubator/metrics-server"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ff253ad767462c46be284da12dda33e8"

PROTOCOL = "https"
SRCREV = "a65d286d5ee14c4c5bf47112cb341d849fd40644"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-metrics;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "0.3.3"
MINOR_VERSION = "1"
DEPENDENCY_MANAGER_VERSION = "0.5.0"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "metrics_server"
DOCKER_EXTRA_ARG += " \
    --build-arg METRICS_SERVER=${MAJOR_VERSION} \
    --build-arg DEPENDENCY_MANAGER=${DEPENDENCY_MANAGER_VERSION} \
"
