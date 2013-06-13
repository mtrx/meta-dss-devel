DESCRIPTION = "script to configure the RS485 port"
SECTION = "base"
PRIORITY = "required"
DEPENDS = "makedevs"
RDEPENDS = "makedevs"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"
PR = "r3"

SRC_URI = "file://ds485init.sh"

inherit update-rc.d

INITSCRIPT_NAME = "ds485init"
INITSCRIPT_PARAMS = "defaults 1"

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/ds485init.sh ${D}${sysconfdir}/init.d/ds485init
}

pkg_preinst_${PN}() {

#!/bin/sh
if test "x$D" != "x"; then
    OPT="-r $D"
else
    OPT=""
fi

if test -e "/etc/init.d/${INITSCRIPT_NAME}"; then
    update-rc.d -f $OPT ${INITSCRIPT_NAME} remove
fi

}
