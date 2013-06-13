# dSM configuration update
DESCRIPTION = "Tool to update digitalSTROM Meter configuration"
DEPENDS = "libdsm-api"
LICENSE = "CLOSED"
PR="r1"

SRC_URI[md5sum] = "62838f5f8dabf466c4c7b84b19f42b31"
SRC_URI[sha256sum] = "031c1a4662352f30d79817e218226ca1d1239f61ed195fff4edf40bed80384d4"

SRC_URI = "http://developer.digitalstrom.org/download/dsm-firmware-upgrade/dsm-config-update-${PV}.tar.gz"

inherit autotools

