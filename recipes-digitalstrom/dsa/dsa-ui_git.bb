# web user interface for the dSA
DESCRIPTION = "digitalSTROM Assistant web interface"
LICENSE="GPLv3"
LIC_FILES_CHKSUM="file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = "yoke-native"
RDEPENDS_${PN} = "dsa"

inherit gitpkgv

PR="r3"

SRCREV="${AUTOREV}"
PKGV = "0.9.3+gitr${GITPKGV}"
PV = "${PKGV}"


SRC_URI="git://gitorious.digitalstrom.org/ds-assistant/ds-assistant-ui.git;protocol=git;branch=master"

S = "${WORKDIR}/git"

inherit autotools

EXTRA_OEMAKE = "'CC=${CCACHE} ${BUILD_PREFIX}gcc' 'CXX=${CCACHE} ${BUILD_PREFIX}g++' 'LD=${CCACHE} ${BUILD_PREFIX}ld'"

require dsa-webmodule-icons.inc

do_install_append() {
    install -d ${D}/www/pages/js/dsa/css
    install -d ${D}/www/pages/locale/de_DE/LC_MESSAGES
    install -d ${D}/www/pages/locale/fr_CH/LC_MESSAGES
    install -d ${D}/www/pages/locale/en_US/LC_MESSAGES
    install -d ${D}/www/pages/locale/nl_NL/LC_MESSAGES
    install -d ${D}/www/pages/locale/tr_TR/LC_MESSAGES
    install -d ${D}/www/pages/images
    install ${S}/webroot/js/dsa/dsa-interface-module.js ${D}/www/pages/js/dsa/dsa-interface-module.js
    install ${S}/webroot/js/dsa/css/dsa_icons.css ${D}/www/pages/js/dsa/css/dsa_icons.css
    install ${S}/webroot/locale/de_DE/LC_MESSAGES/dsa-langpack.json ${D}/www/pages/locale/de_DE/LC_MESSAGES/dsa-langpack.json
    install ${S}/webroot/locale/fr_CH/LC_MESSAGES/dsa-langpack.json ${D}/www/pages/locale/fr_CH/LC_MESSAGES/dsa-langpack.json
    install ${S}/webroot/locale/en_US/LC_MESSAGES/dsa-langpack.json ${D}/www/pages/locale/en_US/LC_MESSAGES/dsa-langpack.json
    install ${S}/webroot/locale/nl_NL/LC_MESSAGES/dsa-langpack.json ${D}/www/pages/locale/nl_NL/LC_MESSAGES/dsa-langpack.json
    install ${S}/webroot/locale/tr_TR/LC_MESSAGES/dsa-langpack.json ${D}/www/pages/locale/tr_TR/LC_MESSAGES/dsa-langpack.json

    for icon in ${INSTALL_ICONS}; do
        install ${S}/webroot/images/$icon ${D}/www/pages/images/$icon
    done
}

PACKAGE_ARCH_${PN} = "all"
PACKAGE_ARCH_${PN}-module = "all"

PACKAGES += "${PN}-module"
FILES_${PN}-module = "/www/*"

