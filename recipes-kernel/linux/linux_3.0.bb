require linux.inc

PR = "r8"

S = "${WORKDIR}/linux-${PV}"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_dss11-1gb = "1"

SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-${PV}.tar.bz2;name=kernel \
           ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-${PV}.17.bz2;apply=yes;name=patch \
           "

SRC_URI[kernel.md5sum] = "398e95866794def22b12dfbc15ce89c0"
SRC_URI[kernel.sha256sum] = "64b0228b54ce39b0b2df086109a7b737cde58e3df4f779506ddcaccee90356a0"

SRC_URI[patch.md5sum] = "8beef6d04bfa8b26446378682b332cfe"
SRC_URI[patch.sha256sum] = "4e54083019c6b21b2074f56ec40b6b38a0a32635d48cea7ac99d772c0f6f23b1"

SRC_URI_append_dss11-1gb = " \
        file://0001-basic-support-for-dss11-boot.patch;apply=yes \
        file://0002-dss11-leds.patch;apply=yes \
        file://0003-dss11-buttons.patch;apply=yes \
        file://0005-RS485-enable-by-default.patch;apply=yes \
        file://0006-don-t-disable-RS485-RX-during-TX.patch;apply=yes \
        file://0008-defconfig.patch;apply=yes \
        file://0009-enable-wifi-modules.patch;apply=yes \
        file://0011-align-NAND-settings-with-U-Boot-settings.patch;apply=yes \
        file://0012-enable-4-bit-BCH-ECC-instead-of-1-bit-Hamming-ECC.patch;apply=yes \
        file://0013-arm-at91-fix-NAND-bus-width-decoding-from-system_rev.patch;apply=yes \
        file://0014-mtd-nand-restore-useage-of-ONFI-reported-bus-width.patch;apply=yes \
        file://0015-enable-compilation-of-BCH-code.patch;apply=yes \
        file://0016-replace-MMC-with-SPI-NOR-flash.patch;apply=yes \
	file://0018-re-enable-external-RTC.patch;apply=yes \
	file://0019-atmel_serial-force-TXenable-line-to-be-low-deactivat.patch \
	file://defconfig \
        "
