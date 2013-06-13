# telnet daemon configuration
DESCRIPTION = "xinetd configuration to enable telnetd"
RDEPENDS_${PN} = "xinetd busybox"
SECTION = "base"
LICENSE = "GPL"
PR = "r0"

SRC_URI = "file://telnetd.xinetd"

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/xinetd.d
    install -m 0755 ${WORKDIR}/telnetd.xinetd ${D}${sysconfdir}/xinetd.d/telnetd
}

pkg_postinst_${PN}() {
    if [[ -x /etc/init.d/xinetd ]]; then
        /etc/init.d/xinetd restart
    fi
}

pkg_postrm_${PN}() {
    if [[ -x /etc/init.d/xinetd ]]; then
        /etc/init.d/xinetd restart
    fi
}

PACKAGE_ARCH_${PN} = "all"

