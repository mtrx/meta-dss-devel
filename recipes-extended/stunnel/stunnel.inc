SECTION = "console/network"
DEPENDS = "openssl"
HOMEPAGE = "http://www.stunnel.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM="file://COPYING;md5=06715c92fdfd7a3dcf901b26b162b64c"

DESCRIPTION = "Stunnel is a program that allows you to encrypt \
arbitrary TCP connections inside SSL"

PR="r10"

S = "${WORKDIR}/stunnel-${PV}"

inherit autotools

EXTRA_OECONF = "--with-ssl=${STAGING_LIBDIR}/.. \
		--with-random=/dev/urandom \
		"

do_install_append() {
    install -d ${D}${sysconfdir}/stunnel
    install -m 644 ${WORKDIR}/stunnel.conf ${D}${sysconfdir}/stunnel
}

FILES_${PN} += "${sysconfdir}"

pkg_preinst_${PN}() {
#/bin/sh

update-rc.d stunnel remove

mkdir -p /var/run/stunnel

if test -e "/etc/runit/stunnel/supervise/stat"; then
    if test `cat /etc/runit/stunnel/supervise/stat` = "run"; then
        echo "Stopping stunnel..."
        sv -w 15 down /etc/runit/stunnel
    fi
fi
}

pkg_postinst_${PN}() {
#/bin/sh
if test -d "/etc/runit/stunnel"; then
    rm -rf /etc/runit/stunnel
fi
}

pkg_postrm_${PN}() {
#/bin/sh
if test -d "/etc/runit/stunnel"; then
    rm -rf /etc/runit/stunnel
fi
}
