# digitalSTROM Server build file

DESCRIPTION="digitalSTROM Server"
HOMEPAGE="http://developer.digitalstrom.org"
MAINTAINER="dss-developer@forum.digitalstrom.org"
LICENSE="GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = "boost gsoap libical js libxml2 avahi libdsm-api openssl rrdtool zlib curl expat libjson libcommchannel protobuf"
RDEPENDS_${PN} += "svlogd2 runit ds485d openssl-so-links create-certificate dss11-ledd msmtp rrdtool"
INC_PR="85"
ARM_INSTRUCTION_SET = "arm"

SRC_URI += " file://dss.logrotate \
             file://dss.run \
             file://dss.finish \
             file://dss.log.run \
             file://sim.xml \
             file://getdsmfwversion.sh"

inherit autotools

EXTRA_OECONF = " --disable-soap --disable-libtcmalloc --enable-http --with-gsoap-import=${STAGING_DATADIR}/gsoap/import --with-boost=${STAGING_DIR_TARGET}/usr --enable-mail-spool --with-mail-dir=/tmp/mailq --with-distro-version-file=/etc/version"

CXXFLAGS_append = " -fno-optimize-sibling-calls"

do_compile_prepend() {
    echo "${PKGV}" > ${S}/.dss_git_version
}

do_install_append () {
    install -m 0755 -d ${D}${sysconfdir}/runit/dss/log
    install -m 0755 -d ${D}${datadir}/dss
    install -m 0755 -d ${D}${datadir}/dss/data/savedprops
    install -m 0755 -d ${D}${datadir}/dss/data/metering
    install -m 0755 -d ${D}${datadir}/dss/data/config.d
    install -m 0755 -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/getdsmfwversion.sh ${D}${bindir}/getdsmfwversion.sh
    install -m 0755 ${WORKDIR}/dss.run ${D}${sysconfdir}/runit/dss/run
    install -m 0755 ${WORKDIR}/dss.finish ${D}${sysconfdir}/runit/dss/finish
    install -m 0755 ${WORKDIR}/dss.log.run ${D}${sysconfdir}/runit/dss/log/run
    install -m 0755 ${S}/build/dsstests ${D}${datadir}/dss/dsstests
    install -m 0755 ${S}/tools/ds3 ${D}${bindir}/ds3
    install -m 0755 -d ${D}${sysconfdir}/logrotate.d
    install ${WORKDIR}/dss.logrotate ${D}${sysconfdir}/logrotate.d/dss
    install -m 0644 ${WORKDIR}/sim.xml ${D}${datadir}/dss/data/sim.xml
    install -m 0755 -d ${D}${datadir}/dss-web/webroot
    #install -m 0644 ${S}/build/webservices/dss.wsdl ${D}${datadir}/dss-web/webroot/dss.wsdl
}

pkg_postinst_${PN}() {
#!/bin/sh

if [[ -f "/etc/runit/dss/supervise/stat" ]]; then
    if [[ "`cat /etc/runit/dss/supervise/stat`" == "down" ]]; then
        echo "Starting dSS..."
        sv up /etc/runit/dss
        if [[ $? -ne 0 ]]; then
            echo "sv command return error, could not start dSS!"
        fi
    fi

    rm -f /var/log/dss/js/*.log.1.*
fi
 
}

pkg_prerm_${PN}() {
#!/bin/sh

if [[  -f "/etc/runit/dss/supervise/stat" ]]; then
    if [[ "`cat /etc/runit/dss/supervise/stat`" == "run" ]]; then
        echo "Stopping dSS..."
        sv -w 5 force-stop /etc/runit/dss
        if [[ $? -ne 0 ]]; then
            echo "sv command returned error, killing dSS process"
            killall -9 dss
        else
            sleep 3
        fi
    fi
fi

# safeguard to make sure dSS is really down
if [[ -n "`pidof dss`" ]]; then
    killall -9 dss
    sleep 1
fi

}

pkg_preinst_${PN}() {
#!/bin/sh

if [[ ! -f "/etc/ssl/certs/dsscert.pem" ]]; then
    mkdir -p /etc/ssl/certs
    create-cert /etc/ssl/certs
fi

if [[ -f "/etc/runit/dss/supervise/stat" ]]; then
    if [[ "`cat /etc/runit/dss/supervise/stat`" == "run" ]]; then
        echo "Stopping dSS..."
        sv -w 5 force-stop /etc/runit/dss
        if [[ $? -ne 0 ]]; then
            echo "sv command returned error, killing dSS process"
            killall -9 dss
        else
            sleep 3
        fi
    fi
fi

# safeguard to make sure dSS is really down
if [[ -n "`pidof dss`" ]]; then
    killall -9 dss
    sleep 1
fi

}

pkg_postrm_${PN}() {
#!/bin/sh

if [[ -d "/etc/runit/dss" ]]; then
    rm -rf /etc/runit/dss
fi

}

CONFFILES_${PN} = "${datadir}/dss/data/sim.xml"

PACKAGES =+ "${PN}-tests ${PN}-tests-dbg ${PN}-ds3 "
FILES_${PN} += " ${datadir}/dss-web"
FILES_${PN}-tests = "${datadir}/dss/dsstests"
FILES_${PN}-tests-dbg += " ${datadir}/dss/.debug/dsstests "

FILES_${PN}-ds3 = "${bindir}/ds3"
RDEPENDS_${PN}-ds3 = "dss curl"
PACKAGE_ARCH_${PN}-ds3 = "all"
