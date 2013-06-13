FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PRINC = "4"

RREPLACES_${PN} = "cron"
RCONFLICTS_${PN} = "cron"
RPROVIDES_${PN} = "cron"

SRC_URI += "file://crond.sysconfig"

do_install_append() {
    install -d ${D}${sysconfdir}/sysconfig/
    install -m 0644 ${WORKDIR}/crond.sysconfig ${D}${sysconfdir}/sysconfig/crond
}
