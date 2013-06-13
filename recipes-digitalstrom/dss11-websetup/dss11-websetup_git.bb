# setup interface for the whole system
DESCRIPTION = "dSS11 web setup UI"
DEPENDS = "yoke-native"
RDEPENDS_${PN} = "lighttpd lighttpd-module-cgi haserl gettext-runtime tzdata opkg2json dss-add-on-feeds dsm-firmware-upgrade dsm-firmware msmtp libxml2-utils release-notes ntp at util-linux-flock"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM="file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

inherit gitpkgv

PR = "r1"
SRCREV="${AUTOREV}"
PKGV = "0.34.5+gitr${GITPKGV}"
PV = "${PKGV}"

SRC_URI = "git://gitorious.digitalstrom.org/dss11-websetup/dss11-websetup.git;protocol=git;branch=master \
           file://dss11-websetup.logrotate"

S = "${WORKDIR}/git"

inherit autotools

# sd card support in the UI is disabled by default, enable only for old hardware
EXTRA_OECONF_at91sam9g20ek = "--enable-sdcard --enable-reboot-after-update"

EXTRA_OEMAKE = "'CC=${CCACHE} ${BUILD_PREFIX}gcc' 'CXX=${CCACHE} ${BUILD_PREFIX}g++' 'LD=${CCACHE} ${BUILD_PREFIX}ld'"

# somehow I failed to get rid of the prefix...
do_install_append() {
    install -d ${D}/www/
    mv ${D}/usr/share/dss11-websetup/www ${D}/www/pages
    mv ${D}/usr/share/dss11-websetup/scripts ${D}/www/scripts
    rm -rf ${D}/usr

    sed -i s:LOCALEDIR=\"/usr/share/www/pages/locale\":LOCALEDIR=\"/www/pages/locale\":g ${D}/www/pages/cgi-bin/langdefs.sh
    install -d ${D}/www/pages/add-ons/
    install -m 0755 -d ${D}${sysconfdir}/logrotate.d
    install -m 0644 ${WORKDIR}/dss11-websetup.logrotate ${D}${sysconfdir}/logrotate.d/dss11-websetup
}

FILES_${PN} = "/www/* ${sysconfdir}/logrotate.d/*"

PACKAGE_ARCH = "${MACHINE_ARCH}"
