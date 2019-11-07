# Overview 

meta-akraino is an OpenEmbedded/Yocto compatible layer for Akraino

# Project License

Copyright (C) 2019 Wind River Systems, Inc.

Source code included in the tree for individual recipes is under the LICENSE
stated in the associated recipe (.bb file) unless otherwise stated.

The metadata is under the following license unless otherwise stated.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

# Dependencies
This layer depends on the following layers:
  * oe-core version supplied with Wind River Linux 10.18
  * oe-core
  * intel-x86
  * meta-anaconda
  * meta-dpdk
  * meta-intel
  * meta-openembedded
  * meta-realtime
  * meta-secure-core
  * meta-security
  * meta-selinux
  * meta-virtualization
  * meta-starlingx
  * meta-security
  * meta-rauc
  * meta-cloud-services
  * wrlinux
  * wr-template
  * wrlinux-x

# Maintenance
This layer is maintained by Wind River Systems, Inc.
Contact "Huang Jackie <jackie.huang@windriver.com>" or "Gooch, Stephen <stephen.gooch@windriver.com>"

# Prerequisite

> Note: It's in initial stage now, and based on the work in meta-starlingX and wrlinux 10.18

* Host requirements
  * Your host need to meet the requirements for Yocto, please refer to:
    * [Compatible Linux Distribution](https://www.yoctoproject.org/docs/2.6.3/brief-yoctoprojectqs/brief-yoctoprojectqs.html#brief-compatible-distro)
    * [Supported Linux Distributions](https://www.yoctoproject.org/docs/2.6.3/ref-manual/ref-manual.html#detailed-supported-distros)
    * [Required Packages for the Build Host](https://www.yoctoproject.org/docs/2.6.3/ref-manual/ref-manual.html#required-packages-for-the-build-host)
  * Ensure docker >= 18.09.2 is installed and the docker.service is running.
  * The docker group is created and the user for building is added into the group:
  ```
  $ sudo groupadd docker
  $ sudo usermod -aG docker $USER
  ```
  * Verify that you can run docker commands without sudo:
  ```
  $ docker run hello-world
  ```

# Steps to build the image

### Use wrapper script build_akraino.sh to build the image

```
$ wget http://stash.wrs.com/users/jhuang0/repos/meta-akraino/raw/scripts/build_akraino.sh
$ chmod +x build_akraino.sh
$ WORKSPACE=/path/to/workspace
$ ./build_akraino.sh ${WORKSPACE}
```

If all go well, you will get the ISO image in:
${WORKSPACE}/prj_wrl1018_akraino/tmp-glibc/deploy/images/intel-x86-64/akraino-image-rec-intel-x86-64.iso

If you meet failures (it's very likely to happen since the layer is not well tested for now),
you may need to check the following manual steps, figure out what step is failed and re-run manually.

### Manual Steps to setup build project and build the image

#### 1. Clone the source of WRLinux BASE 10.18 from github and setup

```shell
# Create directories for wrlinux source and build project
$ export WORKSPACE=/path/to/workspace
$ export SRC_WRL_DIR=${WORKSPACE}/src_wrl1018
$ export SRC_EXTRA_DIR=${WORKSPACE}/src_extra_layers
$ export PRJ_BUILD_DIR=${WORKSPACE}/prj_wrl1018_akraino
$ mkdir -p ${SRC_WRL_DIR} ${PRJ_BUILD_DIR} ${SRC_EXTRA_DIR}

# Clone the source
$ cd $SRC_WRL_DIR
$ git clone --branch WRLINUX_10_18_BASE \
  git://github.com/WindRiver-Labs/wrlinux-x.git

# Setup
$ ./wrlinux-x/build_akraino.sh --machines intel-x86-64
```

#### 2. Clone meta-akraino layer and required layers

```
# clone the repos
$ cd $SRC_EXTRA_DIR
$ git clone http://stash.wrs.com/scm/~jhuang0/meta-akraino.git
$ git clone --branch thud git://github.com/rauc/meta-rauc.git
$ git clone --branch thud git://git.yoctoproject.org/meta-security

# There are some fixes and workaround in these layers, so use the
# personal foked ones for now
$ git clone --branch WRLINUX_10_18_BASE_akraino http://stash.wrs.com/scm/~jhuang0/meta-starlingx.git 
$ git clone --branch WRLINUX_10_18_BASE_akraino git://github.com/jackiehjm/meta-cloud-services.git
```

#### 3. Source the build env

```shell
$ cd $SRC_WRL_DIR
$ . ./environment-setup-x86_64-wrlinuxsdk-linux
$ . ./oe-init-build-env $PRJ_BUILD_DIR
```


#### 4. Add the meta-akraino layer and required layers
```
$ cd $PRJ_BUILD_DIR
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-akraino
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-starlingx
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-rauc
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-security
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-security/meta-tpm
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-security/meta-security-compliance
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-cloud-services
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-cloud-services/meta-openstack
```

#### 5. Add extra configs into local.conf

```
$ docker_bin=`which docker`
$ cat << EOF >> conf/local.conf
#######################
# Configs for Akraino #
#######################
DISTRO = "akraino"
BB_NO_NETWORK = '0'
docker_bin = "${docker_bin}"
EOF
```

#### 6. Build the Akraino image
```
$ bitbake akraino-image-rec
```

# Legal Notices

All product names, logos, and brands are property of their respective owners. All company, product and service names used in this software are for identification purposes only. Wind River is registered trademarks of Wind River Systems, Inc. UNIX is a registered trademark of The Open Group.

Disclaimer of Warranty / No Support: Wind River does not provide support and maintenance services for this software, under Wind River’s standard Software Support and Maintenance Agreement or otherwise. Unless required by applicable law, Wind River provides the software (and each contributor provides its contribution) on an “AS IS” BASIS, WITHOUT WARRANTIES OF ANY KIND, either express or implied, including, without limitation, any warranties of TITLE, NONINFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE. You are solely responsible for determining the appropriateness of using or redistributing the software and assume any risks associated with your exercise of permissions under the license.

