# dS485 core library
DESCRIPTION = "dS485 core library"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4641e94ec96f98fabc56ff9cc48be14b"
HOMEPAGE = "http://gitorious.digitalstrom.org/ds485-stack/ds485-core"

SRC_URI="git://gitorious.digitalstrom.org/ds485-stack/ds485-core.git"
S = "${WORKDIR}/git"
PV = "1.1.4+gitr${SRCPV}"
SRCREV = "${AUTOREV}"
PR="r1"

inherit cmake

require ds485-core.inc

LEAD_SONAME = "libds485-core.so.1"

FILES_${PN} = " ${libdir}/libds485-core.so.* "
FILES_${PN}-dbg = " ${libdir}/.debug/libds485-core.* "

