require recipes-core/images/core-image-base.bb

IMAGE_DEV_MANAGER = "udev"
IMAGE_INIT_MANAGER = "sysvinit sysvinit-inittab sysvinit-pidof"
IMAGE_INITSCRIPTS = " "
IMAGE_LINGUAS = "de-de en-us"
SPLASH = " "

# our config on the rootfs sets temp to /var/lib/opkg which fails on the host
IPKG_ARGS =+ "--tmp_dir=/tmp"

DEPENDS += "task-dss-${RELEASE_TYPE}-image"

IMAGE_INSTALL += " \
    task-dss-${RELEASE_TYPE}-image \
    "

# only add
IMG_NAME = "dss11-${RELEASE_TYPE}-image"
IMG_NAME_append_arm = "-${DATETIME}"

export IMAGE_BASENAME = "${IMG_NAME}"

