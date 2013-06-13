DESCRIPTION = "zd1201 wifi files for the Linux kernel"
HOMEPAGE = "http://linux-lc100020.sourceforge.net/"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/linux-lc100020/zd1201-${PV}-fw.tar.gz"

SRC_URI[md5sum] = "07a4febc365121f975e2c5e59791d55d"
SRC_URI[sha256sum] = "56fd11578b0fc13947786fca0e1b4227f04b0f00cf190fd3d3bc4dd005ba4267"

S = "${WORKDIR}"

do_compile() {
	:
}

do_install() {
	install -d  ${D}/lib/firmware/
	cp -rpP zd1201-${PV}-fw/zd1201*.fw ${D}/lib/firmware
}

FILES_${PN} += "/lib/firmware/*"
PACKAGE_ARCH = "all"
