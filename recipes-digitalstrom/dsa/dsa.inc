# dS Assistant
DESCRIPTION = "digitalSTROM Assistant - troubleshoot and test dS installations"
MAINTAINER="Sergey 'Jin' Bostandzhyan <jin@dev.digitalstrom.org>"
HOMEPAGE = "http://developer.digitalstrom.org/"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = "libdsm-api libconfig libjson sqlite3 boost gettext libcommchannel protobuf"
RDEPENDS_${PN} = "openssl-so-links ds485d dsa-config"

INC_PR="21"

SRC_URI += "\ 
            file://dsa.run \
            file://dsa.finish \
            file://dsa.log.run \
            file://dsa.logrotate \
            file://dsa.cfg"

inherit autotools gettext

EXTRA_OECONF = " --with-boost-libdir=${STAGING_LIBDIR} "

do_configure_prepend () {
    autoreconf -i
}

do_install_append() {
    install -m 0755 -d ${D}${sysconfdir}/logrotate.d
    install -m 0755 -d ${D}${sysconfdir}/runit/dsa
    install -m 0755 -d ${D}${sysconfdir}/runit/dsa/log
    install -m 0755 -d ${D}${datadir}/dsa/webroot
    install -m 0755 -d ${D}${sysconfdir}/dsa
    install -m 0755 -d ${D}/var/lib/dsa

    install -m 0755 ${WORKDIR}/dsa.run ${D}${sysconfdir}/runit/dsa/run
    install -m 0755 ${WORKDIR}/dsa.finish ${D}${sysconfdir}/runit/dsa/finish
    install -m 0755 ${WORKDIR}/dsa.log.run ${D}${sysconfdir}/runit/dsa/log/run
    install -m 0755 ${WORKDIR}/dsa.cfg ${D}${sysconfdir}/dsa/dsa.cfg

    install ${WORKDIR}/dsa.logrotate ${D}${sysconfdir}/logrotate.d/dsa
}

pkg_postinst_${PN}() {
#!/bin/sh

if [[ -f "/etc/runit/dsa/supervise/stat" ]]; then
    if [[ "`cat /etc/runit/dsa/supervise/stat`" == "down" ]]; then
        echo "Starting dSA..."
        sv up /etc/runit/dsa
        if [[ $? -ne 0 ]]; then
            echo "sv command return error, could not start dSA!"
        fi
    fi
fi

}

pkg_prerm_${PN}() {
#!/bin/sh

if [[  -f "/etc/runit/dsa/supervise/stat" ]]; then
    if [[ "`cat /etc/runit/dsa/supervise/stat`" == "run" ]]; then
        echo "Stopping dSA..."
        sv -w 5 force-stop /etc/runit/dsa
        if [[ $? -ne 0 ]]; then
            echo "sv command returned error, killing dSA process"
            killall -9 dsa
        else
            sleep 4
        fi
    fi
fi

# safeguard to make sure dSA is really down
if [[ -n "`pidof dsa`" ]]; then
    killall -9 dsa
    sleep 1
fi

}

pkg_preinst_${PN}() {
#!/bin/sh

if [[ -f "/etc/runit/dsa/supervise/stat" ]]; then
    if [[ "`cat /etc/runit/dsa/supervise/stat`" == "run" ]]; then
        echo "Stopping dSA..."
        sv -w 5 force-stop /etc/runit/dsa
        if [[ $? -ne 0 ]]; then
            echo "sv command returned error, killing dSA process"
            killall -9 dsa
        else
            sleep 3
        fi
    fi
fi

# safeguard to make sure dSA is really down
if [[ -n "`pidof dsa`" ]]; then
    killall -9 dsa
    sleep 1
fi

}

pkg_postrm_${PN}() {
#!/bin/sh

if [[ -d "/etc/runit/dsa" ]]; then
    rm -rf /etc/runit/dsa
fi

}

