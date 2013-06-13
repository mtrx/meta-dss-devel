# dSA device config files
DESCRIPTION = "dSA device configuration data"
HOMEPAGE = "https://gitorious.digitalstrom.org/ds-assistant/ds-assistant-config"
LICENSE = "CLOSED"

inherit autotools gitpkgv

SRC_URI = "git://gitorious.digitalstrom.org/ds-assistant/ds-assistant-config.git;protocol=git;branch=master"

PR = "r1"
SRCREV="${AUTOREV}"
PKGV = "0.1.0+gitr${GITPKGV}"
PV = "${PKGV}"

S="${WORKDIR}/git"

FILES_${PN} = "/var/lib/dsa/device_config/*"

PACKAGE_ARCH_${PN} = "all"
