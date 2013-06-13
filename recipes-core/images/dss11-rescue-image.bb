# rescue / update image for dss11
LICENSE = "GPLv3+"

IMAGE_INSTALL += "\
    mtd-utils \
    lighttpd \
    lighttpd-module-cgi \
    lighttpd-runit \
    avahi-daemon \
    avahi-autoipd \
    kernel-module-ext3 \
    kernel-module-ext4 \
    kernel-module-vfat \
    kernel-module-ntfs \
    sdcard-init \
    setupinit \
    u-boot-utils \
    haserl \
    "

export IMAGE_BASENAME = "dss11-rescue-image"
