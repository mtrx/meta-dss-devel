# digitalSTROM Server build file

SRC_URI="git://gitorious.digitalstrom.org/dss/dss-mainline.git;protocol=git;branch=master \
         file://boost-mt.patch \
         file://boost-m4.patch"

inherit gitpkgv

PR = "r1.${INC_PR}"
SRCREV="${AUTOREV}"
PKGV = "1.18.0+gitr${GITPKGV}"
PV = "${PKGV}"

S="${WORKDIR}/git"

INSANE_SKIP_dss = "True"

require dss.inc

do_install_prepend() {
    install -d ${S}/data/webroot
}
