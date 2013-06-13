#dSA

SRC_URI = "git://gitorious.digitalstrom.org/ds-assistant/ds-assistant.git;protocol=git;branch=master"

require dsa.inc

inherit gitpkgv

PR = "r1.${INC_PR}"
SRCREV="${AUTOREV}"
PKGV = "0.15.4+gitr${GITPKGV}"
PV = "${PKGV}"

S="${WORKDIR}/git"
