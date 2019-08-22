# meta-akraino
OpenEmbedded/Yocto compatible layer for Akraino

This is an initial layer to port Akraino to Yocto linux, and it's not ready for consumption yet.

## How to use

> Note: It's in initial porting stage now, and based on the work in meta-starlingX, no buildable image for Akraino for now.

### 1. Setup yocto build project with meta-starlingX

Refer to the steps in [meta-starlingX README](https://github.com/zbsarashki/meta-starlingX/blob/master/README) to setup a yocto build project

### 2. Clone and add meta-akraino layer

```
# clone the akraino repo
cd /path/to/repo_dir/layers
git clone https://github.com/jackiehjm/meta-akraino.git

# add the meta-akraino layer
cd /path/to/prj/dir
bitbake-layers add-layer /path/to/repo_dir/layers/meta-akraino
```

### 3. Add or port Akraino specific packages and depended packages

Akraino specific packages should be added in recipes-akraino directory, 
the depended packges should be added in other recipes-XYZ according to
the package types.

### 4. Build and test

* build the package only
```
bitbake <pkg_name>
```

* add the package in image meta-akraino/recipes-core/images/akraino-image-host.bb and build

> Note: the image is not ready yet

```
bitbake akraino-image-host
```
