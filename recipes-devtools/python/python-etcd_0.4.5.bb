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

DESCRIPTION = "A python client for Etcd"
HOMEPAGE = "https://github.com/jplana/python-etcd"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=b2dea313538da5a58817e832f27ded07"

SRC_URI[md5sum] = "a636872dcaf5595ec6f71c83322b9924"
SRC_URI[sha256sum] = "f1b5ebb825a3e8190494f5ce1509fde9069f2754838ed90402a8c11e1f52b8cb"

inherit setuptools pypi

PYPI_PACKAGE = "python-etcd"

RDEPENDS_${PN} += " \
    python-urllib3 \
    python-dnspython \
    "
