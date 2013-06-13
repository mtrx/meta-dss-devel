# SpiderMonkey

DESCRIPTION = "A JavaScript engine"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM="file://jsapi.cpp;beginline=5;endline=38;md5=ea5b6c0a7bc36d128cf6db45356e5395"
SECTION = "libs/network"
DEPENDS = "readline nspr zip-native"

RCONFLICTS_${PN} = "js-threadsafe"
RREPLACES_${PN} = "js-threadsafe"

PR = "r1"

SRC_URI = "http://ftp.mozilla.org/pub/mozilla.org/js/mozjs17.0.0.tar.gz \
           file://jsautocfg.tmp \
           "
#           file://use_prepared_config.patch;striplevel=3 \

SRC_URI[md5sum] = "20b6f8f1140ef6e47daa3b16965c9202"
SRC_URI[sha256sum] = "321e964fe9386785d3bf80870640f2fa1c683e32fe988eeb201b04471c172fba"

inherit autotools binconfig

S = "${WORKDIR}/mozjs${PV}/js/src"

export HOST_CC="gcc"
export HOST_CXX="g++"
export HOST_CFLAGS="-DXP_UNIX"
export HOST_CXXFLAGS="-DXP_UNIX"

EXTRA_OECONF = " \
  --enable-threadsafe \
  --enable-cpp-exceptions \
  --disable-tests \
  --without-x --disable-xterm-updates \
"
#  --disable-polyic \
#  --disable-tracejit \
#  --disable-methodjit-spew \
#  --with-nspr-cflags=-DJS_THREADSAFE \
#  --enable-cpp-rtti \

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
    # js produces links to work directory... TODO: fix build scripts
    #rm -f ${D}${libdir}/libmozjs185.so ${D}${libdir}/libmozjs185.so.1.0
    #ln -s libmozjs185.so.1.0 ${D}${libdir}/libmozjs185.so
    #ln -s libmozjs185.so.1.0.0 ${D}${libdir}/libmozjs185.so.1.0
}

PACKAGES =+ "libmozjs"
RDEPENDS_${PN} = "libmozjs"
FILES_${PN} = "${bindir}/js"
FILES_libmozjs = "${libdir}/lib*${SOLIBS} ${base_libdir}/*${SOLIBS}"
FILES_${PN}-dev += "${bindir}/js-config"
AUTO_LIBNAME_PKGS = "libmozjs"

