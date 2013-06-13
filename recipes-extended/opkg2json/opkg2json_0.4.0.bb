# opkg2json
DESCRIPTION = "A utility to query opkg and provide the output as JSON"
MAINTAINER="Sergey 'Jin' Bostandzhyan <jin@dev.digitalstrom.org>"
HOMEPAGE = "http://developer.digitalstrom.org/"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "libjson opkg"
PR="r1"

SRC_URI = "http://developer.digitalstrom.org/download/utilities/opkg2json-${PV}.tar.gz"

inherit autotools pkgconfig 

SRC_URI[md5sum] = "e7a6b2c1a1163394cc0729422dce0143"
SRC_URI[sha256sum] = "0e4367592bb54db30c6c865fe5559d746780b1c8b3843256c6bd72052b17d703"
