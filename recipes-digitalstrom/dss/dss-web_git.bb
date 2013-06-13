# dSS UI moduledSS UI module
DESCRIPTION = "dSS web setup UI"
RDEPENDS_${PN} = "dss"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
PR = "r6"

inherit gitpkgv

SRCREV="${AUTOREV}"
PKGV = "1.14.0+gitr${GITPKGV}"
PV = "${PKGV}"

SRC_URI = "git://gitorious.digitalstrom.org/dss-websrc/dss-websrc-mainline.git;protocol=git;branch=master"

S="${WORKDIR}/git"

inherit autotools

EXTRA_OECONF = "--enable-debug"
EXTRA_OEMAKE = "'CC=${CCACHE} ${BUILD_PREFIX}gcc' 'CXX=${CCACHE} ${BUILD_PREFIX}g++' 'LD=${CCACHE} ${BUILD_PREFIX}ld'"

# this link is needed so that hiding behind lighttpd works correctly
do_install_append() {
    install -d ${D}/www/pages/js/dss/css
    install -d ${D}/www/pages/locale/de_DE/LC_MESSAGES
    install -d ${D}/www/pages/locale/fr_CH/LC_MESSAGES
    install -d ${D}/www/pages/locale/en_US/LC_MESSAGES
    install -d ${D}/www/pages/locale/nl_NL/LC_MESSAGES
    install -d ${D}/www/pages/locale/tr_TR/LC_MESSAGES
    install -d ${D}/www/pages/images
    install ${S}/webroot/js/dss/dss-interface-module.js ${D}/www/pages/js/dss/dss-interface-module.js
    install ${S}/webroot/js/dss/css/dss_icons.css ${D}/www/pages/js/dss/css/dss_icons.css
    install ${S}/webroot/locale/de_DE/LC_MESSAGES/dss-langpack.json ${D}/www/pages/locale/de_DE/LC_MESSAGES/dss-langpack.json
    install ${S}/webroot/locale/fr_CH/LC_MESSAGES/dss-langpack.json ${D}/www/pages/locale/fr_CH/LC_MESSAGES/dss-langpack.json
    install ${S}/webroot/locale/en_US/LC_MESSAGES/dss-langpack.json ${D}/www/pages/locale/en_US/LC_MESSAGES/dss-langpack.json
    install ${S}/webroot/locale/nl_NL/LC_MESSAGES/dss-langpack.json ${D}/www/pages/locale/nl_NL/LC_MESSAGES/dss-langpack.json
    install ${S}/webroot/locale/tr_TR/LC_MESSAGES/dss-langpack.json ${D}/www/pages/locale/tr_TR/LC_MESSAGES/dss-langpack.json

    cp ${S}/webroot/images/*.png ${D}/www/pages/images/
    rm ${D}/www/pages/images/ds_logo.png
}

PACKAGE_ARCH_${PN} = "all"
PACKAGE_ARCH_${PN}-module = "all"

PACKAGES += "${PN}-module"
FILES_${PN}-module = "/www/*"

RREPLACES_${PN}-module = "dss-web"
RCONFLICTS_${PN}-module = "dss-web"
