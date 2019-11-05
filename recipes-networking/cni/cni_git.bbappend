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

do_compile_prepend() {
    unset GOCACHE
}

do_compile_append() {
    # build ipam plugins
    cd ${S}/src/import/vendor/github.com/containernetworking/plugins/
    PLUGINS="$(ls -d plugins/ipam/*)"
    mkdir -p ${WORKDIR}/plugins/bin/
    for p in $PLUGINS; do
        plugin="$(basename "$p")"
        echo "building: $p"
        go build -o ${WORKDIR}/plugins/bin/$plugin github.com/containernetworking/plugins/$p
    done
}
