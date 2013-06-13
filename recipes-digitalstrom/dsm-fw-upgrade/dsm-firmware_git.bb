# dSM firmware
DESCRIPTION = "digitalSTROM Meter firmware"
HOMEPAGE = "http://developer.digitalstrom.org/download/dsm-firmware-images"
RDEPENDS_${PN} = "dsm-firmware-upgrade dsm-config-update"
LICENSE = "CLOSED"

inherit autotools gitpkgv

SRC_URI = "file://do-perform-dsm-upgrade \
           git://gitorious.digitalstrom.org/dss-misc/dsm-firmware-images.git;protocol=git;branch=master"

PR = "r1"
SRCREV="${AUTOREV}"
PKGV = "1.12.0+gitr${GITPKGV}"
PV = "${PKGV}"

S="${WORKDIR}/git"

do_install_append() {
    install -m 0755 -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/do-perform-dsm-upgrade ${D}${bindir}/do-perform-dsm-upgrade
}

FILES_${PN} = "${datadir}/${PN}/* ${bindir}/*"

PACKAGE_ARCH_${PN} = "all"
