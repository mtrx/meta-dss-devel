FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PRINC="7"

SRC_URI += "file://ntp.default file://ntpdate.default"

RREPLACES_${PN} = "ntpclient"
RCONFLICTS_${PN} = "ntpclient"
RDEPENDS_${PN} = "ntpdate"

CONFFILES_${PN} = "${sysconfdir}/default/ntpclient"

do_install_append() {
    install -D ${WORKDIR}/ntp.default ${D}${sysconfdir}/default/ntpclient
    install -D ${WORKDIR}/ntpdate.default ${D}${sysconfdir}/default/ntpdate
    # get rid of ntpdate's ifplug script
    rm -rf ${D}/${sysconfdir}/network
}

FILES_${PN} += "${sysconfdir}/default/ntpclient"

FILES_ntpdate = "${bindir}/ntpdate ${sysconfdir}/default/ntpdate"

# do not install ntpdate into crontab
pkg_postinst_ntpdate() {
}
