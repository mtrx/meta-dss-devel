# runit
DESCRIPTION = "A sysvinit replacement with service supervision"
HOMEPAGE = "http://smarden.org/runit"
LICENSE = "BSD-alike"
LIC_FILES_CHKSUM = "file://package/COPYING;md5=c9e8a560732fc8b860b6a91341cc603b"

PR = "r6"

SRC_URI = "http://smarden.org/runit/runit-2.1.1.tar.gz \
           file://cross-compile.patch;striplevel=1 \
           file://build-svwait.patch;striplevel=1 \
           file://forced_reboot.patch;striplevel=1 \
           file://rockify.patch;striplevel=1 \
           file://change_servicedir.patch;striplevel=1 \
           file://runit.sh"


INITSCRIPT_NAME = "runit.sh"
INITSCRIPT_PARAMS = "defaults 80"

inherit autotools update-rc.d

S = "${WORKDIR}/admin/${PN}-${PV}"

TARGET_CC_ARCH += "${LDFLAGS}"

export HOSTCC="${BUILD_PREFIX}gcc"
export CC="${TARGET_PREFIX}gcc -Wl,--hash-style=gnu ${TARGET_CC_ARCH}"

do_compile() {
    ./package/compile
}

do_install() {
    install -d ${D}/${sbindir}
    install -d ${D}/${sysconfdir}/runit
    install -d ${D}${sysconfdir}/init.d
    install ${WORKDIR}/runit.sh ${D}${sysconfdir}/init.d/runit.sh
    for p in `cat ./package/commands`; do
        install -m 0755 ${S}/compile/${p} ${D}/${sbindir}/${p}
    done
}

SRC_URI[md5sum] = "8fa53ea8f71d88da9503f62793336bc3"
SRC_URI[sha256sum] = "ffcf2d27b32f59ac14f2d4b0772a3eb80d9342685a2042b7fbbc472c07cf2a2c"

