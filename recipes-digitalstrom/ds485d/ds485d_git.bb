# dS485 daemon
DESCRIPTION = "dS485 daemon"
LICENSE = "GPLv2"
HOMEPAGE = "http://gitorious.digitalstrom.org/ds485-stack/ds485d"
DEPENDS = "libds485-core"
LIC_FILES_CHKSUM = "file://COPYING;md5=4641e94ec96f98fabc56ff9cc48be14b"

SRC_URI="git://gitorious.digitalstrom.org/ds485-stack/ds485d.git \
         file://ds485d.run \
         file://ds485d.log.run \
         file://ds485d.finish \
         file://ds485d.logrotate \
         "

S = "${WORKDIR}/git"
PV = "1.2.1+gitr${SRCPV}"
SRCREV = "${AUTOREV}"
PR="r2"

inherit cmake

CONFFILES_${PN} = "${sysconfdir}/runit/ds485d/run"

do_install_append() {
    install -m 0755 -d ${D}${sysconfdir}/runit/ds485d/log
    install -m 0755 -d ${D}${sysconfdir}/logrotate.d

    install -m 0755 ${WORKDIR}/ds485d.run ${D}${sysconfdir}/runit/ds485d/run
    install -m 0755 ${WORKDIR}/ds485d.finish ${D}${sysconfdir}/runit/ds485d/finish
    install -m 0755 ${WORKDIR}/ds485d.log.run ${D}${sysconfdir}/runit/ds485d/log/run
    install ${WORKDIR}/ds485d.logrotate ${D}${sysconfdir}/logrotate.d/ds485d
}

pkg_preinst_${PN}() {
#!/bin/sh

if test -e "/etc/runit/ds485d/supervise/stat"; then
    if test `cat /etc/runit/ds485d/supervise/stat` = "run"; then
        echo "Stopping dS485d..."
        sv -w 15 down /etc/runit/ds485d
    fi
fi
}

pkg_prerm_${PN}() {
#!/bin/sh

if test -e "/etc/runit/ds485d/supervise/stat"; then
    if test `cat /etc/runit/ds485d/supervise/stat` = "run"; then
        echo "Stopping dS485d..."
        sv -w 15 down /etc/runit/ds485d
    fi
fi
}

pkg_postrm_${PN}() {
#!/bin/sh

if test -d "/etc/runit/ds485d"; then
    rm -rf /etc/runit/ds485d
fi

if test -d "/var/run/ds485.pid"; then
    rm -f /var/run/ds485.pid
fi
}

pkg_postinst_${PN}() {
#!/bin/sh
if test -e "/etc/runit/ds485d/supervise/stat"; then
    if test `cat /etc/runit/ds485d/supervise/stat` = "down"; then
        echo "Starting dS485d..."
        sv -w 15 up /etc/runit/ds485d
    fi
fi
}

