# dss11 scripts for upgrade via USB
DESCRIPTION = "utilities to allow opkg upgrades from USB"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"
LICENSE = "GPLv3"
HOMEPAGE = "http://developer.digitalstrom.org"
DEPENDS = "udev"

SRC_URI="git://gitorious.digitalstrom.org/dss-misc/dss11-usb-upgrade.git;protocol=git;branch=master"

USB_BASE_PATH = "file://media/dss11usb/dss11-usb-upgrade/"

inherit autotools gitpkgv

PR = "r1"
SRCREV="${AUTOREV}"
PKGV = "1.4.0+gitr${GITPKGV}"
PV = "${PKGV}"

S="${WORKDIR}/git"


do_install_append () {
    install -m 0755 -d ${D}${sysconfdir}/opkg-usb/
    install -m 0755 -d ${D}${sysconfdir}/opkg-add-ons-usb/
    echo ${USB_BASE_PATH} > ${D}${sysconfdir}/opkg-usb/upgrade.repo
    echo ${USB_BASE_PATH} > ${D}${sysconfdir}/opkg-add-ons-usb/upgrade.repo
}

