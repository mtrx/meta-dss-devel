DESCRIPTION = "Synchronize hardware clock with system time once per week"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"
RDEPENDS_${PN} += "busybox cronie"
PR = "r5"

ALLOW_EMPTY_${PN} = "1"

pkg_preinst() {
    #!/bin/sh
    if [ "x$D" != "x" ]; then
        exit 0
    fi
    if [ -f /var/spool/cron/root ]; then
        rm -f /var/spool/cron/root
    fi

    grep -v /sbin/hwclock ${sysconfdir}/crontab | grep -v /usr/sbin/hwclock > ${sysconfdir}/crontab.no-${PF}
    mv ${sysconfdir}/crontab.no-${PF} ${sysconfdir}/crontab
}

pkg_postinst_${PN} () {
    #!/bin/sh

    if [ "x$D" != "x" ]; then
        exit 0
    fi

    grep -q "/sbin/hwclock" ${sysconfdir}/crontab || echo "42 * * * * root /sbin/hwclock -f /dev/rtc1 --systohc >/dev/null 2>&1" >> ${sysconfdir}/crontab
}

pkg_prerm_${PN} () {
    #!/bin/sh

    grep -v /sbin/hwclock ${sysconfdir}/crontab | grep -v /usr/sbin/hwclock > ${sysconfdir}/crontab.no-${PF}
    mv ${sysconfdir}/crontab.no-${PF} ${sysconfdir}/crontab
}

PACKAGE_ARCH = "all"
