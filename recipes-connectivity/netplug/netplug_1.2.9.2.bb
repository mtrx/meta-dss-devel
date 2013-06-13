DESCRIPTION = "daemon that responds to network link events"
SECTION = "console/network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=965705ab631267711efe8bf89e7e3cb5"

PR="r3"

SRC_URI = "http://www.red-bean.com/~bos/${PN}/${PN}-${PV}.tar.bz2 \
	file://init file://netplugd.conf file://netplug"

SRC_URI[md5sum] = "1d6db99536bdf875ce441f2c0e45ebf2"
SRC_URI[sha256sum] = "5180dfd9a7d3d0633a027b0a04f01b45a6a64623813cd48bd54423b90814864e"

INITSCRIPT_NAME = "netplugd"
INITSCRIPT_PARAMS = "defaults 65"
inherit update-rc.d autotools


export prefix=""
export initdir="${sysconfdir}/init.d"


do_install_append() {
	install -D -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/netplugd
	install -D -m 0755 ${WORKDIR}/netplugd.conf ${D}${sysconfdir}/netplug/netplugd.conf
	install -D -m 0755 ${WORKDIR}/netplug ${D}${sysconfdir}/netplug.d/netplug
}

