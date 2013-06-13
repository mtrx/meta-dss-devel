FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PRINC="4"

DEPENDS += "popt"


SRC_URI += "file://logrotate.conf"

do_install_append() {
    rm -f ${D}${sysconfdir}/cron.daily/logrotate
    rmdir ${D}${sysconfdir}/cron.daily
    install -m 0644 ${WORKDIR}/logrotate.conf ${D}${sysconfdir}/logrotate.conf
}

pkg_preinst() {
    #!/bin/sh
    if [ "x$D" != "x" ]; then
        exit 0
    fi
    if [ -f /var/spool/cron/root ]; then
        rm -f /var/spool/cron/root
    fi
}

pkg_postinst() {
    #!/bin/sh
    if [ "x$D" != "x" ]; then
        exit 0
    fi

    # Add the logrotate line to /etc/crontab
    grep -q "${sbindir}/logrotate" ${sysconfdir}/crontab || echo "*/5 * * * * root ${sbindir}/logrotate ${sysconfdir}/logrotate.conf >/dev/null 2>&1" >> ${sysconfdir}/crontab
}

pkg_postrm() {
    # Remove the logrotate line from /etc/crontab
    grep -v ${sbindir}/logrotate ${sysconfdir}/crontab > ${sysconfdir}/crontab.no-${PF}
    mv ${sysconfdir}/crontab.no-${PF} ${sysconfdir}/crontab
}

