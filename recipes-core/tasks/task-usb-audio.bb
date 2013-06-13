DESCRIPTION = "Packages for USB audio support"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"

PR = "r1"

inherit task

RDEPENDS_${PN} = "\
    kernel-module-soundcore \
    kernel-module-snd \
    kernel-module-snd-usb-audio \
    kernel-module-snd-usbmidi-lib \
    kernel-module-snd-rawmidi \
    kernel-module-snd-pcm \
    kernel-module-snd-timer \
    kernel-module-snd-hwdep \
    kernel-module-snd-page-alloc \
    mpg123 \
    alsa-utils \
    alsa-utils-aplay \
    "

COMPATIBLE_MACHINE = "dss11-1gb"
PACKAGE_ARCH = "${MACHINE_ARCH}"
