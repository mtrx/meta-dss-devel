PR = "r3"
LIC_FILES_CHKSUM="file://COPYING;md5=4c6cde5df68eff615d36789dc18edd3b"
require u-boot.inc

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_mpc8315e-rdb = "1"

SRC_URI = "ftp://ftp.denx.de/pub/u-boot/u-boot-${PV}.tar.bz2 \
	file://0001-dss11-changes-by-Emlix.patch \
	file://0002-update-partitions-to-support-rescue-system.patch \
	file://0003-implement-rescue-image-handling-for-dss11.patch \
	file://0004-boot-different-system-depending-on-values-of-bootsel.patch \
	file://0005-add-some-testing-commands.patch \
	file://0006-Simplify-output-of-dssgpio-command.patch \
	file://0007-change-the-default-to-boot-the-productive-system.patch \
	file://0008-add-additional-board-files.patch \
	file://0009-ignore-uclean-error.patch \
    file://dont-inline-weak-symbols2.patch;striplevel=1 \
"

UBOOT_MACHINE_at91sam9g20ek = "digitalSTROM_config"

TARGET_LDFLAGS = ""

inherit base

do_compile () {
	oe_runmake ${UBOOT_MACHINE}
	oe_runmake all
}

SRC_URI[md5sum] = "dfbe65c1e31bb7de5f5b03d50de192b5"
SRC_URI[sha256sum] = "b0037cf21b67779ef5a0c585b32e46bde3b78df889484c78bb4318c9b448f560"
