# dSM firmware upgrade tool
DESCRIPTION = "dSM firmware upgrade tool"
LICENSE = "GPLv2"
HOMEPAGE = "http://gitorious.digitalstrom.org/dss-misc/dsm-fw-upgrade"
DEPENDS = "libdsm-api"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit autotools gettext gitpkgv 

SRC_URI="file://perform-dsm-upgrade \
         file://dsm-firmware-upgrade.logrotate \
         git://gitorious.digitalstrom.org/dss-misc/dsm-fw-upgrade.git;protocol=git;branch=master"

PR = "r1"
SRCREV="${AUTOREV}"
PKGV = "0.12.0+gitr${GITPKGV}"
PV = "${PKGV}"

S="${WORKDIR}/git"

do_configure_prepend () {
    autoreconf -i
}

do_install_append () {
    install -m 0755 -d ${D}${bindir}
    install -m 0755 -d ${D}${sysconfdir}/logrotate.d

    install -m 0755 ${WORKDIR}/perform-dsm-upgrade ${D}${bindir}/perform-dsm-upgrade
    install -m 0644 ${WORKDIR}/dsm-firmware-upgrade.logrotate ${D}${sysconfdir}/logrotate.d/dsm-firmware-upgrade
}
