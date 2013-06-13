# SpiderMonkey

DESCRIPTION = "A JavaScript engine"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM="file://jsapi.cpp;beginline=5;endline=38;md5=f83ddfc6e78ef82f4ddd7c4c0567d10d"
SECTION = "libs/network"
DEPENDS = "readline nspr zip-native"

RCONFLICTS_${PN} = "js-threadsafe"
RREPLACES_${PN} = "js-threadsafe"

PR = "r4"

SRC_URI = "http://ftp.mozilla.org/pub/mozilla.org/js/js185-1.0.0.tar.gz \
           file://use_prepared_config.patch;striplevel=3 \
           file://armv7ahfp.patch;striplevel=3 \
           file://jsautocfg.tmp \
           "

SRC_URI[md5sum] = "a4574365938222adca0a6bd33329cb32"
SRC_URI[sha256sum] = "5d12f7e1f5b4a99436685d97b9b7b75f094d33580227aa998c406bbae6f2a687"

inherit autotools binconfig

S = "${WORKDIR}/js-${PV}/js/src"

export HOST_CC="gcc"
export HOST_CXX="g++"
export HOST_CFLAGS="-DXP_UNIX"
export HOST_CXXFLAGS="-DXP_UNIX"

EXTRA_OECONF = " \
  --enable-threadsafe \
  --enable-cpp-rtti \
  --enable-cpp-exceptions \
  --disable-tests \
  --without-x --disable-xterm-updates \
  --with-nspr-cflags=-DJS_THREADSAFE \
  --disable-methodjit-spew \
  --disable-polyic \
  --disable-tracejit \
  --enable-valgrind \
  --disable-optimize \
  --enable-debug \
"

#EXTRA_OECONF_append_arm = "--with-cpu-arch=armv5te" 

export EXTRA_LIBS = "-lnspr4 -lplc4 -lplds4"

# avoid running autotools_do_configure which forces autoreconf to run
do_configure() {
  install ${WORKDIR}/jsautocfg.tmp ${S}/jsautocfg.tmp
  oe_runconf
}

do_compile() {
  oe_runmake CROSS_COMPILE=${TARGET_ARCH}
}

do_install_append () {
    install -d ${D}${bindir}
    install ${S}/shell/js ${D}${bindir}
    # js produces links to work directory... TODO: fix build scripts
    rm -f ${D}${libdir}/libmozjs185.so ${D}${libdir}/libmozjs185.so.1.0
    ln -s libmozjs185.so.1.0 ${D}${libdir}/libmozjs185.so
    ln -s libmozjs185.so.1.0.0 ${D}${libdir}/libmozjs185.so.1.0
}

PACKAGES =+ "libmozjs"
RDEPENDS_${PN} = "libmozjs"
FILES_${PN} = "${bindir}/js"
FILES_libmozjs = "${libdir}/lib*${SOLIBS} ${base_libdir}/*${SOLIBS}"
FILES_${PN}-dev += "${bindir}/js-config"
AUTO_LIBNAME_PKGS = "libmozjs"

