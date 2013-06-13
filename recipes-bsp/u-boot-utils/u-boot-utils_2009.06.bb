DESCRIPTION = "U-boot bootloader OS env. access tools"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=4c6cde5df68eff615d36789dc18edd3b"

DEPENDS = "mtd-utils"
PR = "r2"

SRC_URI = "ftp://ftp.denx.de/pub/u-boot/u-boot-${PV}.tar.bz2 \
	file://0001-dss11-changes-by-Emlix.patch;patch=1;apply=yes \
	file://0002-update-partitions-to-support-rescue-system.patch;patch=1;apply=yes \
	file://0003-implement-rescue-image-handling-for-dss11.patch;patch=1;apply=yes \
	file://0004-boot-different-system-depending-on-values-of-bootsel.patch;patch=1;apply=yes \
        file://fw_env.config \
"

S = "${WORKDIR}/u-boot-${PV}"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"
TARGET_LDFLAGS = ""

do_configure() {
        :
}

do_compile () {
        oe_runmake digitalSTROM_config
        oe_runmake env
}

do_install () {
        install -d      ${D}/sbin
        install -d      ${D}${sysconfdir}
        install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
        install -m 755 ${S}/tools/env/fw_printenv ${D}/sbin/fw_printenv
        install -m 755 ${S}/tools/env/fw_printenv ${D}/sbin/fw_setenv
}

