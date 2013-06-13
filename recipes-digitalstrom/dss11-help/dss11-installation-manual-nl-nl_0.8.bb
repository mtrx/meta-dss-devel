# help web pages
DESCRIPTION = "dSS11 german installation manual"
RDEPENDS_${PN} = "lighttpd"
LICENSE = "CLOSED"
PR = "r2"

#MANUAL_BASENAME = "digitalSTROM-InstallationManual_V008-DE_2012-12-10"

#SRC_URI = "http://developer.digitalstrom.org/download/doc/${MANUAL_BASENAME}.zip"

# S = "${WORKDIR}/${MANUAL_BASENAME}"
SRC_URI = "file://index.html"

do_install () {
    install -m 0755 -d ${D}/www/pages/help/nl_NL
    install ${WORKDIR}/index.html ${D}/www/pages/help/nl_NL/index.html
}
#mv ${S}/manual/* ${D}/www/pages/help/nl_NL

FILES_${PN} = "/www/pages/help/nl_NL/*"

PACKAGE_ARCH_${PN} = "all"

#SRC_URI[md5sum] = "4002db5c8d8fdab4f1ff05e3786da7c8"
#SRC_URI[sha256sum] = "49f3ac7aab762f151eeed0178a45c3d966072a65f7dc908b66dc3342201dda9d"

