COMPATIBLE_HOST = "(i.86|x86_64).*-linux"

DEPENDS = "rsync-native xz-native"

# This will use the docker from build host.
# Please ensure the docker is installed and work fine
docker_bin ?= "/usr/bin/docker"

docker_build_dir = "${S}/docker-build"
docker_save_dir = "${B}/docker-save"

DOCKER_ARG_PROXY = "\
    --build-arg HTTP_PROXY=${http_proxy} \
    --build-arg HTTPS_PROXY=${https_proxy} \
    --build-arg NO_PROXY=${no_proxy} \
    --build-arg http_proxy=${http_proxy} \
    --build-arg https_proxy=${https_proxy} \
    --build-arg no_proxy=${no_proxy} \
"

DOCKER_EXTRA_ARG ?= ""
COMPONENT ?= "${BPN}"
IMAGE_TAG = "${PV}"

do_compile () {
	# build the docker image
	${docker_bin} build \
		--network=host \
		--no-cache \
		--force-rm \
		${DOCKER_ARG_PROXY} \
		${DOCKER_EXTRA_ARG} \
		--tag ${COMPONENT}:${IMAGE_TAG} \
		${docker_build_dir}/${COMPONENT}

	# create a save folder and save the docker image
	mkdir -p ${docker_save_dir}
	${docker_bin} save ${COMPONENT}:${IMAGE_TAG} | xz -z -T2 \
		> ${docker_save_dir}/${COMPONENT}:${IMAGE_TAG}.tar

	# remove the docker image
	${docker_bin} rmi ${COMPONENT}:${IMAGE_TAG}
}

do_install () {
	install -d ${D}${caas_container_tar_path}
	rsync -rlpD ${docker_save_dir}/${COMPONENT}:${IMAGE_TAG}.tar \
		${D}${caas_container_tar_path}
}

FILES_${PN} = "${caas_container_tar_path}"
