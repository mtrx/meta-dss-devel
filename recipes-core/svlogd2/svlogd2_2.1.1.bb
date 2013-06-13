# runit
DESCRIPTION = "A modified svlogd version from the runit package that provides a better integration with logrotate"
HOMEPAGE = "http://smarden.org/runit"
LICENSE = "BSD-alike"
LIC_FILES_CHKSUM = "file://package/COPYING;md5=c9e8a560732fc8b860b6a91341cc603b"

PR = "r3"

SRC_URI = "http://smarden.org/runit/runit-2.1.1.tar.gz \
           file://cross-compile.patch;striplevel=1 \
           file://build-svwait.patch;striplevel=1 \
           file://forced_reboot.patch;striplevel=1 \
           file://rockify.patch;striplevel=1 \
           file://svlogd.patch;striplevel=1 \
           file://runit.sh"

inherit autotools

S = "${WORKDIR}/admin/runit-${PV}"

TARGET_CC_ARCH += "${LDFLAGS}"

export HOSTCC="${BUILD_PREFIX}gcc"
export CC="${TARGET_PREFIX}gcc -Wl,--hash-style=gnu ${TARGET_CC_ARCH}"

do_compile() {
    ./package/compile
}

do_install() {
    install -d ${D}/${sbindir}
    install -m 0755 ${S}/compile/svlogd ${D}/${sbindir}/svlogd2
}

SRC_URI[md5sum] = "8fa53ea8f71d88da9503f62793336bc3"
SRC_URI[sha256sum] = "ffcf2d27b32f59ac14f2d4b0772a3eb80d9342685a2042b7fbbc472c07cf2a2c"
