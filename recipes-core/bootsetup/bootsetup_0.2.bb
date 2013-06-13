# boot setup recipe
DESCRIPTION = "Perform various system configurations on boot time"
SECTION = "base"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"
RDEPENDS_${PN} = "create-certificate"
PR = "r1"

SCRIPTFILE="bootsetup"

SRC_URI = "file://${SCRIPTFILE}"

inherit update-rc.d

INITSCRIPT_NAME = "${SCRIPTFILE}"
# after sd card script
INITSCRIPT_PARAMS = "defaults 37"

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/${SCRIPTFILE} ${D}${sysconfdir}/init.d/${SCRIPTFILE}
}

PACKAGE_ARCH_${PN} = "all"

pkg_postinst_${PN}() {
#/bin/sh
if [ ! -s "/etc/ssl/certs/dsscert.pem" ]; then
   mkdir -p /etc/ssl/certs
   create-cert /etc/ssl/certs
fi
}

