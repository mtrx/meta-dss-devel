DESCRIPTION = "LuaBitOp"
SECTION = "libs"
LIC_FILES_CHKSUM="file://README;beginline=9;endline=10;md5=02bf11dada4f034cc812625e0fb5e19c"
LICENSE = "MIT"

BV = "1.0.1"
PV = "${BV}"
PR = "r0"

DEPENDS = "lua5.1"

SRC_URI="http://bitop.luajit.org/download/LuaBitOp-${PV}.tar.gz"
S = "${WORKDIR}/LuaBitOp-${PV}"

SRC_URI[md5sum] = "39984456940aea838e0f500bececbd73"
SRC_URI[sha256sum] = "6afa9984411079a7e109977bc8a28d63890c1489a928b1f61d33ef327e9cb0c9"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
	mkdir -pv ${D}${libdir}/lua/5.1
	install -v -m 644 ${S}/bit.so ${D}/${libdir}/lua/5.1
}

FILES_${PN} += "${libdir}/lua"
