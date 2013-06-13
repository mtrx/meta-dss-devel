# dSS11 ledd daemon
DESCRIPTION = "dSS11 ledd daemon"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

HOMEPAGE = "http://gitorious.digitalstrom.org/dss-misc/dss11-ledd"

DEPENDS = "boost"

SRC_URI=" \
    file://dss11-ledd.run \
    file://dss11-ledd.log.run \
    file://dss11-ledd.check \
    file://dss11-ledd.finish \
    file://leds \
    file://dss11-ledd.logrotate \
    git://gitorious.digitalstrom.org/dss-misc/dss11-ledd.git;protocol=git;branch=master"

inherit autotools gitpkgv

PR = "r2"
SRCREV="${AUTOREV}"
PKGV = "0.3.0+gitr${GITPKGV}"
PV = "${PKGV}"

S="${WORKDIR}/git"


do_install_append () {
    install -m 0755 -d ${D}${sysconfdir}/runit/dss11-ledd/log

    install -m 0755 ${WORKDIR}/dss11-ledd.run ${D}${sysconfdir}/runit/dss11-ledd/run
    install -m 0755 ${WORKDIR}/dss11-ledd.log.run ${D}${sysconfdir}/runit/dss11-ledd/log/run
    install -m 0755 ${WORKDIR}/dss11-ledd.check ${D}${sysconfdir}/runit/dss11-ledd/check
    install -m 0755 ${WORKDIR}/dss11-ledd.finish ${D}${sysconfdir}/runit/dss11-ledd/finish
    
    install -m 0755 -d ${D}${sysconfdir}/netplug.d
    install -m 0755 ${WORKDIR}/leds ${D}${sysconfdir}/netplug.d/leds

    install -m 0755 -d ${D}${sysconfdir}/logrotate.d
    install ${WORKDIR}/dss11-ledd.logrotate ${D}${sysconfdir}/logrotate.d/dss11-ledd
}

pkg_preinst_${PN}() {
#!/bin/sh

if test -e "/etc/runit/dss11-ledd/supervise/stat"; then
    if test `cat /etc/runit/dss11-ledd/supervise/stat` = "run"; then
        echo "Stopping dSS11 LED daemon..."
        sv -w 15 down /etc/runit/dss11-ledd
    fi
fi
}

pkg_prerm_${PN}() {
#!/bin/sh

if test -e "/etc/runit/dss11-ledd/supervise/stat"; then
    if test `cat /etc/runit/dss11-ledd/supervise/stat` = "run"; then
        echo "Stopping dSS11 LED daemon..."
        sv -w 15 down /etc/runit/dss11-ledd
    fi
fi
}

pkg_postrm_${PN}() {
#!/bin/sh

if test -d "/etc/runit/dss11-ledd"; then
    rm -rf /etc/runit/dss11-ledd
fi
}

pkg_postinst_${PN}() {
#!/bin/sh

if test -e "/etc/runit/dss11-ledd/supervise/stat"; then
    if test `cat /etc/runit/dss11-ledd/supervise/stat` = "down"; then
        echo "Starting dSS11 LED daemon..."
        sv -w 15 up /etc/runit/dss11-ledd
    fi
fi

if [ -f /var/lock/systemupdate ]; then
    dss11-ledd-client "system_update_started"
fi

# set ready flag
dss11-ledd-client "ready"

# work around race-conditions
if [ ! -f /var/lock/systemupdate ]; then
    dss11-ledd-client "system_update_completed"
fi

# we will get an error if dbus is not running, but we don't want this hook to 
# fail
exit 0
}
