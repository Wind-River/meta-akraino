SUMMARY = "Containers as a Service Elasticsearch component"
DESCRIPTION = "This RPM contains the Elasticsearch container image for CaaS subsystem."
HOMEPAGE = "https://github.com/elastic/elasticsearch"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

PROTOCOL = "https"
SRCREV = "18a4bea203375dcc595992f2b5679348958e33d0"

SRC_URI = "git://gerrit.akraino.org/r/ta/caas-logging;protocol=${PROTOCOL};rev=${SRCREV}"

S = "${WORKDIR}/git"

inherit docker-build

MAJOR_VERSION = "7.3.0"
MINOR_VERSION = "0"

PV = "${MAJOR_VERSION}-${MINOR_VERSION}"

COMPONENT = "elasticsearch"
DOCKER_EXTRA_ARG += "--build-arg VERSION=${MAJOR_VERSION}"
