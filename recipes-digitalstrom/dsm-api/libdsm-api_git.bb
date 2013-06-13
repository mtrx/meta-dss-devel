# dSM API
DESCRIPTION = "dS485 dSM API"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=9eef91148a9b14ec7f9df333daebc746"
HOMEPAGE = "http://gitorious.digitalstrom.org/ds485-stack/dsm-api"
DEPENDS = "python libds485-client python python-native python-lxml-native swig-native lua5.1"

PR="r1"

SRC_URI="git://gitorious.digitalstrom.org/ds485-stack/dsm-api.git"

S = "${WORKDIR}/git"

PV = "2.6.1+gitr${SRCPV}"
SRCREV = "${AUTOREV}"

inherit cmake python-dir

EXTRA_OECMAKE = "-DCMAKE_SWIG_ADDITIONAL_INCLUDE=${STAGING_INCDIR} -DPYTHON_LIBRARY_PATH=${PYTHON_SITEPACKAGES_DIR} -DCMAKE_SKIP_RPATH=TRUE -DWITH_SCRIPTING=on"

PACKAGES += "${PN}-python-dbg ${PN}-python"
FILES_${PN}-python-dbg = "${PYTHON_SITEPACKAGES_DIR}/dsmapi2/.debug"
FILES_${PN}-python = "${bindir}/* ${PYTHON_SITEPACKAGES_DIR}/dsmapi2/*.so ${PYTHON_SITEPACKAGES_DIR}/dsmapi2/*.py"
RDEPENDS_${PN}-python = "${PN} python"

PACKAGES += "${PN}-lua ${PN}-lua-dbg"
FILES_${PN}-lua-dbg = "${libdir}/lua/5.1/.debug/*"
FILES_${PN}-lua = "${libdir}/lua/5.1/*.so"
RDEPENDS_${PN}-lua = "${PN}"

FILES_${PN} = "${libdir}/*.so.*"

pkg_preinst_${PN}() {
#!/bin/sh
if test -e "/etc/runit/dss/supervise/stat"; then
    if test `cat /etc/runit/dss/supervise/stat` = "run"; then
        echo "Stopping dSS..."
        sv -w 15 down /etc/runit/dss
    fi
fi

if test -e "/etc/runit/dsa/supervise/stat"; then
    if test `cat /etc/runit/dsa/supervise/stat` = "run"; then
        echo "Stopping dSA..."
        sv -w 15 down /etc/runit/dsa
    fi
fi

}

pkg_prerm_${PN}() {
#!/bin/sh
if test -e "/etc/runit/dss/supervise/stat"; then
    if test `cat /etc/runit/dss/supervise/stat` = "run"; then
        echo "Stopping dSS..."
        sv -w 15 down /etc/runit/dss
    fi
fi

if test -e "/etc/runit/dsa/supervise/stat"; then
    if test `cat /etc/runit/dsa/supervise/stat` = "run"; then
        echo "Stopping dSA..."
        sv -w 15 down /etc/runit/dsa
    fi
fi

}

pkg_postinst_${PN}() {
#!/bin/sh
if test -e "/etc/runit/dss/supervise/stat"; then
    if test `cat /etc/runit/dss/supervise/stat` = "down"; then
        echo "Starting dSS..."
        sv -w 15 up /etc/runit/dss
    fi
fi

if test -e "/etc/runit/dsa/supervise/stat"; then
    if test `cat /etc/runit/dsa/supervise/stat` = "down"; then
        echo "Starting dSA..."
        sv -w 15 up /etc/runit/dsa
    fi
fi

}
