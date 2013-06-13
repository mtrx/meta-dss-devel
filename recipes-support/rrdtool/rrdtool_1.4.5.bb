# recipe for rrdtool
DESCRIPTION = "High performance data logging and graphing system for time series data."
HOMEPAGE = "http://oss.oetiker.ch/rrdtool/"
SECTION = "utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=44fee82a1d2ed0676cf35478283e0aa0"

DEPENDS = "zlib libxml2 glib-2.0 cairo pango libpng"
PR = "r5"

# split library, examples and main applications
PACKAGES =+ "${PN}-examples librrd librrd-dev"
RDEPENDS_${PN} = "librrd"
FILES_librrd = "${libdir}/lib*${SOLIBS}"
FILES_librrd-dev = "${libdir}/*.a ${libdir}/*.la"
FILES_librrd-dbg = "${libdir}/.debug"
FILES_${PN}-examples = "${datadir}/${PN}/*"
AUTO_LIBNAME_PKGS = "librrd"

# configure patch for time_t size already integrated upstream
# unbreak_norrdgraph.patch submitted upstream as Ticket #323
# the ieee math patch hacks out the macro that no longer works in OE core
# for some reason
SRC_URI = "http://oss.oetiker.ch/rrdtool/pub/rrdtool-${PV}.tar.gz \
        file://0001-use-AC_CHECK_SIZEOF-for-time_t-type-check.patch \
        file://configure-ac.patch \
        file://changeset_2172.diff \
        file://unbreak_norrdgraph.patch \
        file://ieee_math_hack.patch \
	    file://rrdcached.run \
	    file://rrdcached.finish \
	    "

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

}

pkg_postrm_${PN}() {
#!/bin/sh

if [[ -d "/etc/runit/rrdcached" ]]; then
    rm -rf /etc/runit/rrdcached
fi

}

SRC_URI[md5sum] = "4d116dba9a0888d8aaac179e35d3980a"
SRC_URI[sha256sum] = "1267af420533d846432e55352db89b0f2507c711f80c65e016f7484f3cb0cf1d"
