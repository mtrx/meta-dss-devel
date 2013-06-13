PR = "r1"
LIC_FILES_CHKSUM="file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
require u-boot.inc

DEPENDS += "srecord-native"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_dss11-1gb = "1"

#PROVIDES = "u-boot"

SRC_URI = "ftp://ftp.denx.de/pub/u-boot/u-boot-${PV}.tar.bz2 "

UBOOT_MACHINE = "digitalSTROM_nandflash_config"

SRC_URI_append_dss11-1gb = " \
	file://0001-port-dss11-changes-from-Emlix-to-current-u-boot-vers.patch \
	file://0002-update-partitions-to-support-rescue-system.patch \
	file://0003-implement-rescue-image-handling-for-dss11.patch \
	file://0004-boot-different-system-depending-on-values-of-bootsel.patch \
	file://0005-add-some-testing-commands.patch \
	file://0006-Simplify-output-of-dssgpio-command.patch \
	file://0007-change-the-default-to-boot-the-productive-system.patch \
	file://0008-allow-overwriting-mac-address.patch \
	file://0009-change-mtd-partitions-to-fit-bigger-u-boot.patch \
	file://0010-make-NAND-bus-width-dependent-on-board_variant.patch \
	file://0011-enable-BCH-ECC-encoding.patch \
	file://0012-enable-ONFI-detection.patch \
	file://0013-recognize-board-variants-2-and-3-also.patch \
	file://0014-changes-for-dSS11-V005-6.patch \
	file://0015-change-rootfs-from-jffs2-to-ubifs.patch \
	file://0016-tftp-flash-large-nand-images.patch \
	file://0017-enable-direct-TFTP-to-FLASH-writing.patch \
	file://0018-increase-TFTP-buffer-size.patch \
	file://0019-remove-JFFS2-commands.patch \
	file://0020-increase-size-of-kernel-partitions.patch \
	file://boot-at91sam9g20-ek-serialflash2sdram.bin \
"

TARGET_LDFLAGS = ""

SPI_BINARY ?= "at91bootstrap+u-boot.bin"
SPI_IMAGE ?= "at91bootstrap+u-boot-${MACHINE}-${PV}-${PR}.bin"
SPI_SYMLINK ?= "at91bootstrap+u-boot-${MACHINE}.bin"

inherit base

do_compile () {
       oe_runmake ${UBOOT_MACHINE}
       oe_runmake all
       srec_cat ../boot-at91sam9g20-ek-serialflash2sdram.bin -binary -exclude 0x14 0x18 -little_endian_length 0x14 4 -fill 0xff 0x0 0x10000 ${UBOOT_BINARY} -binary -offset 0x10000 -fill 0xff 0x10000 0x80000 -output ${SPI_BINARY} -binary
}

do_deploy () {
	install ${S}/${UBOOT_BINARY} ${DEPLOYDIR}/${UBOOT_IMAGE}
	install ${S}/${SPI_BINARY} ${DEPLOYDIR}/${SPI_IMAGE}

	cd ${DEPLOYDIR}
	rm -f ${UBOOT_SYMLINK}
	ln -sf ${UBOOT_IMAGE} ${UBOOT_SYMLINK}

	rm -f ${SPI_SYMLINK}
	ln -sf ${SPI_IMAGE} ${SPI_SYMLINK}
}
addtask deploy before do_build after do_compile

S = "${WORKDIR}/u-boot-${PV}"

SRC_URI[md5sum] = "15a087051d071d608000e138c39545d9"
SRC_URI[sha256sum] = "e08e20a6979bfca6eebb9a2b0e42aa4416af3d796332fd63a3470495a089d496"
