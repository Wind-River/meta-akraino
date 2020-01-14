inherit setuptools
require recipes-devtools/python/python-ansible.inc

SRC_URI[md5sum] = "86f0c18250895338709243d997005de3"
SRC_URI[sha256sum] = "8e9403e755ce8ef27b6066cdd7a4c567aa80ebe2fd90d0ff8efa0a725d246986"

ANSIBLE_WHITELIST_MODULES = "  \
    cloud \
    clustering \
    commands \
    database \
    files \
    identity \
    inventory \
    messaging \
    monitoring \
    net_tools \
    network \
    network/ovs \
    notification \
    packaging \
    remote_management \
    service \
    source_control \
    storage \
    system \
    utilities \
    web_infrastructure \
    __pycache__ \
"

RDEPENDS_${PN} += " \
    python-pyyaml \
    python-jinja2 \
    python-modules \
"
