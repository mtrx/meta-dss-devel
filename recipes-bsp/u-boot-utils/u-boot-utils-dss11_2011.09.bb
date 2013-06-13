DESCRIPTION = "U-boot bootloader OS env. access tools"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "GPL"
LIC_FILES_CHKSUM="file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
DEPENDS = "mtd-utils"
PR = "r2"

SRC_URI = "ftp://ftp.denx.de/pub/u-boot/u-boot-${PV}.tar.bz2 \
        file://tools-Makefile.patch \
        file://env-Makefile.patch \
        file://fw_env.config"

S = "${WORKDIR}/u-boot-${PV}"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"
TARGET_LDFLAGS = ""
FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/u-boot-${PV}"

do_configure() {
        :
}

do_compile () {
        oe_runmake at91sam9g20ek_nandflash_config
        oe_runmake tools
}

do_install () {
        install -d      ${D}/sbin
        install -d      ${D}${sysconfdir}
        install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
        install -m 755 ${S}/tools/env/fw_printenv ${D}/sbin/fw_printenv
        install -m 755 ${S}/tools/env/fw_printenv ${D}/sbin/fw_setenv
}


SRC_URI[md5sum] = "259017613713c01bd9bcc82edc153e4f"
SRC_URI[sha256sum] = "6223d0141d2451dde6a9bfb8abbbdbc3696e1c3d289744d1918296561306ab4b"
