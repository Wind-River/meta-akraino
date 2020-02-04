inherit setuptools
require recipes-devtools/python/python-ansible.inc

SRC_URI[md5sum] = "9a7ac4e4e3883ef4a0f49185d0a6dc4b"
SRC_URI[sha256sum] = "2b02756b9b6bc02d9028a4577ef332f8126c607528e18db825672b0301343358"

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
