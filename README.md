# meta-akraino
OpenEmbedded/Yocto compatible layer for Akraino

This is an initial layer to port Akraino to Yocto linux, and it's not ready for consumption yet.

## How to use

> Note: It's in initial porting stage now, and based on the work in meta-starlingX and wrlinux 10.18

* Prerequiste:
  * Ensure docker >= 18.09.2 is installed and the docker.service is running.
  * The docker group is created and the user for building is added into the group:
  ```
  $ sudo groupadd docker
  $ sudo usermod -aG docker $USER
  ```

### 1. Clone the source of WRLinux BASE 10.18 from github and setup

```shell
# Create directories for wrlinux source and build project
$ export SRC_WRL_DIR=/path/to/src_wrl1018
$ export SRC_EXTRA_DIR=/path/to/src_extra_layers
$ export PRJ_BUILD_DIR=/path/to/prj_wrl1018_akraino
$ mkdir -p $SRC_WRL_DIR $PRJ_BUILD_DIR $SRC_EXTRA_DIR

# Clone the source
$ cd $SRC_WRL_DIR
$ git clone --branch WRLINUX_10_18_BASE \
  git://github.com/WindRiver-Labs/wrlinux-x.git

# Setup
$ ./wrlinux-x/setup.sh --machines intel-x86-64 --layers meta-security \
  meta-cloud-services meta-openstack

```

### 2. Clone meta-akraino layer and required layers

```
# clone the repos
$ cd $SRC_EXTRA_DIR
$ git clone https://github.com/jackiehjm/meta-akraino.git
$ git clone https://github.com/zbsarashki/meta-starlingX.git
$ git clone git://github.com/rauc/meta-rauc.git
```

### 3. Source the build env

```shell
$ cd $SRC_WRL_DIR
$ . ./environment-setup-x86_64-wrlinuxsdk-linux
$ . ./oe-init-build-env $PRJ_BUILD_DIR
```


### 4. Add the meta-akraino layer and required layers
```
$ cd $PRJ_BUILD_DIR
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-akraino
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-starlingX
$ bitbake-layers add-layer $SRC_EXTRA_DIR/meta-rauc
$ bitbake-layers add-layer $SRC_WRL_DIR/layers/meta-security/meta-tpm
```

### 5. Add extra configs into local.conf

```
$ cat << EOF >> conf/local.conf
###############################
# Config for Akraino
###############################
DISTRO = "akraino"
BB_NO_NETWORK = '0'
IMAGE_FSTYPES += "tar.bz2 live wic.vmdk wic.vdi wic.qcow2"
docker_bin = "/usr/bin/docker"
IMAGE_OVERHEAD_FACTOR="1.1"
PNWHITELIST_LAYERS_remove = " \\
    meta-akraino \\
    starlingX-layer \\
    dpdk \\
    tpm-layer \\
    cloud-services-layer \\
    efi-secure-boot \\
    filesystems-layer \\
    integrity \\
    intel \\
    meta-python \\
    networking-layer \\
    openembedded-layer \\
    openstack-layer \\
    realtime \\
    selinux \\
    signing-key \\
    tpm2 \\
    virtualization-layer \\
    webserver \\
    gnome-layer \\
    meta-initramfs \\
    perl-layer \\
    security \\
    rauc \\
    scanners-layer \\
"
EOF

```

### 6. Build the Akraino image
```
$ bitbake akraino-image-host
```
