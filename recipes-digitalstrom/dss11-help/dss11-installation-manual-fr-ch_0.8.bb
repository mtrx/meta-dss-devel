# help web pages
DESCRIPTION = "dSS11 german installation manual"
RDEPENDS_${PN} = "lighttpd"
LICENSE = "CLOSED"
PR = "r1"

MANUAL_BASENAME = "digitalSTROM-InstallationManual_V008-FR_2012-12-10"

SRC_URI = "http://developer.digitalstrom.org/download/doc/${MANUAL_BASENAME}.zip"

S = "${WORKDIR}/${MANUAL_BASENAME}"

do_install () {
    install -m 0755 -d ${D}/www/pages/help/fr_CH
    mv ${S}/manual/* ${D}/www/pages/help/fr_CH
}

FILES_${PN} = "/www/pages/help/fr_CH/*"

PACKAGE_ARCH_${PN} = "all"

SRC_URI[md5sum] = "234cb333c2323ef9db2204fd5fdc7a97"
SRC_URI[sha256sum] = "1079a8df010c239d68c0b3a327ba3040525b180d23130d642e38107d3547e393"

