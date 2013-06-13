# communication channel library
DESCRIPTION = "communication channel library"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
HOMEPAGE = "https://gitorious.digitalstrom.org/dss-misc/libcommchannel"
DEPENDS = "boost"
SRC_URI="git://gitorious.digitalstrom.org/dss-misc/libcommchannel.git"
S = "${WORKDIR}/git"
PV = "0.1.0+gitr${SRCPV}"
SRCREV = "${AUTOREV}"
PR="r1"

inherit autotools

