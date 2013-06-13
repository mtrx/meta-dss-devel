# Netscape Portable Runtime

DESCRIPTION = "Netscape Portable Runtime Library"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
SECTION = "libs/network"

PR = "r2"

SRC_URI = "ftp://ftp.mozilla.org/pub/mozilla.org/nspr/releases/v${PV}/src/nspr-${PV}.tar.gz"

SRC_URI += "file://nspr.pc"

SRC_URI[md5sum] = "60770d45dc08c0f181b22cdfce5be3e8"
SRC_URI[sha256sum] = "ff43c7c819e72f03bb908e7652c5d5f59a5d31ee86c333e692650207103d1cce"

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

