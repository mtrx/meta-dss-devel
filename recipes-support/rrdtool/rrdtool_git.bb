# recipe for rrdtool
DESCRIPTION = "High performance data logging and graphing system for time series data."
HOMEPAGE = "http://oss.oetiker.ch/rrdtool/"
SECTION = "utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=44fee82a1d2ed0676cf35478283e0aa0"

DEPENDS = "zlib libxml2 glib-2.0 cairo pango libpng"
PR = "r1"

# split library, examples and main applications
PACKAGES =+ "${PN}-examples librrd librrd-staticdev"
RDEPENDS_${PN} = "librrd"
FILES_librrd = "${libdir}/lib*${SOLIBS}"
FILES_librrd-staticdev = "${libdir}/*.a ${libdir}/*.la"
FILES_librrd-dbg = "${libdir}/.debug"
FILES_${PN}-examples = "${datadir}/${PN}/*"
AUTO_LIBNAME_PKGS = "librrd"

# the ieee math patch hacks out the macro that no longer works in OE core
# for some reason
SRC_URI = "git://github.com/oetiker/rrdtool-1.x.git;protocol=git;branch=1.4 \
        file://configure-ac-147.patch \
        file://rrdcached.run \
        file://rrdcached.finish \
        "

inherit gitpkgv
SRCREV = "${AUTOREV}"
PKGV = "1.4.7+gitr${GITPKGV}"
PV = "${PKGV}"
S = "${WORKDIR}/git"

inherit autotools gettext

EXTRA_OECONF = "--disable-rrdcgi \
                --disable-tcl \
                --disable-ruby \
                --disable-perl \
                --disable-libwrap \
                --disable-libdbi \
                --disable-python \
                --disable-lua \
                --disable-mmap \
                "
# --disable-rrd_graph \

do_configure_prepend () {
    echo "Running rrdtool autogen.sh"
    sh ./autogen.sh
}

do_install_append () {
    install -m 0755 -d ${D}${sysconfdir}/runit/rrdcached
    install -m 0755 ${WORKDIR}/rrdcached.run ${D}${sysconfdir}/runit/rrdcached/run
    install -m 0755 ${WORKDIR}/rrdcached.finish ${D}${sysconfdir}/runit/rrdcached/finish
}

pkg_postinst_${PN}() {
#!/bin/sh

if [[ -f "/etc/runit/rrdcached/supervise/stat" ]]; then
    if [[ "`cat /etc/runit/rrdcached/supervise/stat`" == "down" ]]; then
        echo "Starting rrdcached..."
        sv up /etc/runit/rrdcached
        if [[ $? -ne 0 ]]; then
            echo "sv command return error, could not start rrdcached!"
        fi
    fi
fi
 
}

pkg_prerm_${PN}() {
#!/bin/sh

if [[  -f "/etc/runit/rrdcached/supervise/stat" ]]; then
    if [[ "`cat /etc/runit/rrdcached/supervise/stat`" == "run" ]]; then
        echo "Stopping rrdcached..."
        sv -w 5 force-stop /etc/runit/rrdcached
        if [[ $? -ne 0 ]]; then
            echo "sv command returned error, killing rrdcached process"
            killall -9 rrdcached
        else
            sleep 3
        fi
    fi
fi

}

pkg_preinst_${PN}() {
#!/bin/sh

if [[ -f "/etc/runit/rrdcached/supervise/stat" ]]; then
    if [[ "`cat /etc/runit/rrdcached/supervise/stat`" == "run" ]]; then
        echo "Stopping rrdcached..."
        sv -w 5 force-stop /etc/runit/rrdcached
        if [[ $? -ne 0 ]]; then
            echo "sv command returned error, killing rrdcached process"
            killall -9 rrdcached
        else
            sleep 3
        fi
    fi
fi

# remove fragments of previous log configuration
if [[ -d "/etc/runit/rrdcached/log" ]]; then
    echo "Removing rrdcached log configuration"
    rm -rf /etc/runit/rrdcached/log
fi

}

pkg_postrm_${PN}() {
#!/bin/sh

if [[ -d "/etc/runit/rrdcached" ]]; then
    rm -rf /etc/runit/rrdcached
fi

}

SRC_URI[md5sum] = "ffe369d8921b4dfdeaaf43812100c38f"
SRC_URI[sha256sum] = "956aaf431c955ba88dd7d98920ade3a8c4bad04adb1f9431377950a813a7af11"
