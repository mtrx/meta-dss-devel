# dS485 client
DESCRIPTION = "dS485 client"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=9eef91148a9b14ec7f9df333daebc746"
HOMEPAGE = "http://gitorious.digitalstrom.org/ds485-stack/ds485-client"
DEPENDS = "libds485-core ds485d"

SRC_URI="git://gitorious.digitalstrom.org/ds485-stack/ds485-client.git"
S = "${WORKDIR}/git"
PV = "1.2.4+gitr${SRCPV}"
SRCREV = "${AUTOREV}"
PR="r1"

LEAD_SONAME = "libds485-client.so.1"

FILES_${PN} = " ${libdir}/libds485-client.so.* "
FILES_${PN}-dbg = " ${libdir}/.debug/libds485-client.* "

inherit cmake

