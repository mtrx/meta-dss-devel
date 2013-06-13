# framework for add-ons
DESCRIPTION = "UI framework for system add-ons"
LICENSE="GPLv3"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI="git://gitorious.digitalstrom.org/dss-add-ons/dss-addon-framework.git;protocol=git;branch=master"

inherit gitpkgv

PR="r1"
SRCREV="${AUTOREV}"
PKGV = "1.0.13+gitr${GITPKGV}"
PV = "${PKGV}"
S="${WORKDIR}/git"

do_configure() {
    :
}

do_install() {
    install -d ${D}/www/pages/framework
    mv ${S}/* ${D}/www/pages/framework/
}

PACKAGE_ARCH_${PN} = "all"
FILES_${PN} = "/www/*"

