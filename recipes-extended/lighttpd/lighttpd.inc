DESCRIPTION = "Lightweight high-performance web server"
HOMEPAGE = "http://www.lighttpd.net/"
BUGTRACKER = "http://redmine.lighttpd.net/projects/lighttpd/issues"

SECTION = "net"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=e4dac5c6ab169aa212feb5028853a579"
DEPENDS = "zlib libpcre openssl"
RDEPENDS_${PN} = "runit create-certificate"
RCONFLICTS_${PN} = "lighttpd-runit"
RREPLACES_${PN} = "lighttpd-runit"
RDEPENDS_${PN} += " \
               lighttpd-module-access \
               lighttpd-module-accesslog \
               lighttpd-module-indexfile \
               lighttpd-module-dirlisting \
               lighttpd-module-redirect \
               lighttpd-module-staticfile \
               lighttpd-module-proxy \
               lighttpd-module-auth \
"

INC_PR = "r43"

SRC_URI = "http://download.lighttpd.net/lighttpd/releases-1.4.x/lighttpd-${PV}.tar.gz \
	   file://lighttpd.conf \
       file://lighttpd.logrotate \
       file://htdigest.sh \
       file://lighttpd.user \
       file://lighttpd.run \
       file://lighttpd.finish \
       file://dirlist.css"

EXTRA_OECONF = " \
             --without-bzip2 \
             --without-ldap \
             --without-lua \
             --without-memcache \
             --with-pcre \
             --without-webdav-props \
             --without-webdav-locks \
             --with-openssl \
             --disable-static \
"

inherit autotools pkgconfig gettext

do_install_append() {
    install -d ${D}${bindir}
    install -d ${D}/www/pages
    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0755 ${WORKDIR}/lighttpd.conf ${D}${sysconfdir}
    ln -s /var/log/ ${D}/www/pages/logs
    install ${WORKDIR}/dirlist.css ${D}/www/pages/dirlist.css
    install ${WORKDIR}/lighttpd.logrotate ${D}${sysconfdir}/logrotate.d/lighttpd
    install ${WORKDIR}/lighttpd.user ${D}${sysconfdir}/lighttpd.user
    install ${WORKDIR}/htdigest.sh ${D}${bindir}/htdigest.sh
    install -m 0755 -d ${D}${sysconfdir}/runit/lighttpd
    install -m 0755 ${WORKDIR}/lighttpd.run ${D}${sysconfdir}/runit/lighttpd/run
    install -m 0755 ${WORKDIR}/lighttpd.finish ${D}${sysconfdir}/runit/lighttpd/finish
}

FILES_${PN} += "${sysconfdir} /www"

CONFFILES_${PN} = "${sysconfdir}/lighttpd.user"

PACKAGES_DYNAMIC = "lighttpd-module-*"

pkg_preinst_${PN}() {
#/bin/sh

if [ -d "/www/pages/logs" ]; then
    rm -rf /www/pages/logs > /dev/null 2>&1
fi

if test -e "/etc/runit/lighttpd/supervise/stat"; then
    if test `cat /etc/runit/lighttpd/supervise/stat` = "run"; then
        echo "Stopping lighttpd..."
        sv -w 15 down /etc/runit/lighttpd
    fi
fi
}

pkg_prerm_${PN}() {
#/bin/sh

if test -e "/etc/runit/lighttpd/supervise/stat"; then
    if test `cat /etc/runit/lighttpd/supervise/stat` = "run"; then
        echo "Stopping lighttpd..."
        sv -w 15 down /etc/runit/lighttpd
    fi
fi
}

pkg_postrm_${PN}() {
#/bin/sh

if test -d "/etc/runit/lighttpd"; then
    rm -rf /etc/runit/lighttpd
fi
}


pkg_postinst_${PN}() {
#/bin/sh
if test -e "/etc/runit/lighttpd/supervise/stat"; then
    if test `cat /etc/runit/lighttpd/supervise/stat` = "down"; then
        echo "Starting lighttpd..."
        sv -w 15 up /etc/runit/lighttpd
        sleep 10
    fi
fi

}

python populate_packages_prepend () {
    lighttpd_libdir = bb.data.expand('${libdir}', d)
    do_split_packages(d, lighttpd_libdir, '^mod_(.*)\.so$', 'lighttpd-module-%s', 'Lighttpd module for %s', extra_depends='')
}

