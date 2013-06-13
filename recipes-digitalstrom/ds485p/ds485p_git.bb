# dS485 proxy daemon
DESCRIPTION = "dS485 proxy daemon"
LICENSE = "GPLv2"
HOMEPAGE = "http://gitorious.digitalstrom.org/ds485-stack/ds485p"
DEPENDS = "libds485-client"
LIC_FILES_CHKSUM = "file://COPYING;md5=4641e94ec96f98fabc56ff9cc48be14b"

PR="r2"

SRC_URI="git://gitorious.digitalstrom.org/ds485-stack/ds485p.git  \
         file://ds485p.run \
         file://ds485p.log.run \
         file://ds485p.finish \
         file://ds485p.logrotate \
         "

S = "${WORKDIR}/git"
PV = "0.0.0+gitr${SRCPV}"
SRCREV = "${AUTOREV}"
PR="r1"

inherit cmake

CONFFILES_${PN} = "${sysconfdir}/runit/ds485p/run"

do_install_append() {
    install -m 0755 -d ${D}${sysconfdir}/runit/ds485p/log
    install -m 0755 -d ${D}${sysconfdir}/logrotate.d

    install -m 0755 ${WORKDIR}/ds485p.run ${D}${sysconfdir}/runit/ds485p/run
    install -m 0755 ${WORKDIR}/ds485p.finish ${D}${sysconfdir}/runit/ds485p/finish
    install -m 0755 ${WORKDIR}/ds485p.log.run ${D}${sysconfdir}/runit/ds485p/log/run
    install ${WORKDIR}/ds485p.logrotate ${D}${sysconfdir}/logrotate.d/ds485p
}

pkg_preinst_${PN}() {
#!/bin/sh

if test -e "/etc/runit/ds485p/supervise/stat"; then
    if test `cat /etc/runit/ds485p/supervise/stat` = "run"; then
        echo "Stopping dS485p..."
        sv -w 15 down /etc/runit/ds485p
    fi
fi
}

pkg_prerm_${PN}() {
#!/bin/sh

if test -e "/etc/runit/ds485p/supervise/stat"; then
    if test `cat /etc/runit/ds485p/supervise/stat` = "run"; then
        echo "Stopping dS485p..."
        sv -w 15 down /etc/runit/ds485p
    fi
fi
}

pkg_postrm_${PN}() {
#!/bin/sh

if test -d "/etc/runit/ds485p"; then
    rm -rf /etc/runit/ds485p
fi

if test -d "/var/run/ds485p.pid"; then
    rm -f /var/run/ds485p.pid
fi
}

pkg_postinst_${PN}() {
#!/bin/sh
if test -e "/etc/runit/ds485p/supervise/stat"; then
    if test `cat /etc/runit/ds485p/supervise/stat` = "down"; then
        echo "Starting dS485p..."
        sv -w 15 up /etc/runit/ds485p
    fi
fi
}

