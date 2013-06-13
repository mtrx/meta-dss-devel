DESCRIPTION = "Metapackage containing useful development tools and utilities"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"

PR = "r9"

inherit task

DEPENDS = "stunnel screen pp40-set-defaults"

RDEPENDS_${PN} = "\
    strace \
    gdb \
    bash \
    ldd \
    procps \
"

RDEPENDS_${PN}_append_i586 = "\
    gcc \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"