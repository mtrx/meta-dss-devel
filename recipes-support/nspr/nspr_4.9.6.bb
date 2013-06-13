# Netscape Portable Runtime

DESCRIPTION = "Netscape Portable Runtime Library"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
SECTION = "libs/network"

PR = "r2"

SRC_URI = "ftp://ftp.mozilla.org/pub/mozilla.org/nspr/releases/v${PV}/src/nspr-${PV}.tar.gz"

SRC_URI += "file://nspr.pc"

SRC_URI[md5sum] = "62b1e9d376d503d972f90c3c0031d879"
SRC_URI[sha256sum] = "7693fddd3c5cc15d53a50df53ab5dcdaa2eb58f5003302690559471744d6c6f9"

S = "${WORKDIR}/nspr-${PV}/mozilla/nsprpub"

inherit autotools 

do_configure() {
	oe_runconf
}

do_compile_prepend() {
	oe_runmake CROSS_COMPILE=1 CFLAGS="-DXP_UNIX" LDFLAGS="" CC=gcc -C config export
}

do_install_append() {
    install -D ${WORKDIR}/nspr.pc ${D}${libdir}/pkgconfig/nspr.pc
}

FILES_${PN} = "/usr/lib/*.so"
FILES_${PN}-dev = "/usr/bin/* /usr/include/* /usr/share/aclocal/* /usr/lib/*.a /usr/lib/pkgconfig/*"
FILES_${PN}-dbg = "/usr/bin/.debug/* /usr/lib/.debug/*"

