DESCRIPTION = "The SRecord package is a collection of powerful tools for manipulating EPROM load files."
HOMEPAGE = "http://srecord.sourceforge.net"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=8dfcbf2f0a144b97f0931b6394debea7"
DEPENDS = "boost-native libgcrypt-native"
PR = "r7"

PARALLEL_MAKE = ""

SRC_URI = "${SOURCEFORGE_MIRROR}/srecord/srecord-${PV}.tar.gz \
	file://srecord-1.57-libtool.patch;apply=yes \
"

SRC_URI[md5sum] = "2c371f75f05273fb05f587c1a36d98b7"
SRC_URI[sha256sum] = "bd6031b5463b77fd15062452fc5fd582a1d4efea2f42275653b3989c4b642feb"

inherit autotools native

do_configure_prepend_virtclass-native() {
    if [[ ! -f /usr/include/boost/shared_ptr.hpp ]]; then
        echo "Please install boost development headers on the host system"
        echo "The package names are boost-devel on Fedora or libboost-dev on \
              Debian/Ubuntu"
        exit 1
    fi
}

do_configure_prepend () {
	mv ${S}/etc/configure.ac ${S}
}
