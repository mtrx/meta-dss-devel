FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PRINC = "2"

CONFFILES_${PN} += "${sysconfdir}/default/dropbear"

SRC_URI += "file://default"

do_install_append() {

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/default ${D}${sysconfdir}/default/dropbear
}
