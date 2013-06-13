DESCRIPTION = "LUA socket"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM="file://LICENSE;md5=9a2d4d6957816b949774b39793409af1"

BV = "2.0.2"
PV = "${BV}"
PR = "r1"

DEPENDS = "lua5.1"

SRC_URI = "http://luaforge.net/frs/download.php/2664/${PN}-${PV}.tar.gz"

SRC_URI[md5sum] = "41445b138deb7bcfe97bff957503da8e"
SRC_URI[sha256sum] = "4fd9c775cfd98841299851e29b30176caf289370fea1ff1e00bb67c2d6842ca6"

S = "${WORKDIR}/${PN}-${PV}"

EXTRA_OEMAKE = "PLATFORM=linux 'CC=${CC}' 'AR=${AR} rcu' 'LD=${CC}' 'CFLAGS=${CFLAGS} -fPIC' 'LDFLAGS=${LDFLAGS} -shared -lgcc'"
CFLAGS_append = " -DLUASOCKET_DEBUG -DLUA_USE_POSIX -DLUA_USE_DLOPEN -DLUA_USE_READLINE "

do_install() {
       mkdir -pv ${D}${libdir}/lua/5.1
       mkdir -pv ${D}${datadir}/lua/5.1
       oe_runmake \
                'INSTALL_TOP=${D}${prefix}' \
                'INSTALL_TOP_LIB=${D}${libdir}/lua/5.1' \
                'INSTALL_TOP_SHARE=${D}${datadir}/lua/5.1' \
                install
}

FILES_${PN} += "${datadir}/lua ${libdir}/lua"
FILES_${PN}-dbg += " ${libdir}/lua/5.1/mime/.debug ${libdir}/lua/5.1/socket/.debug "

