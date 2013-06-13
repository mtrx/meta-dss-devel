
pkg_postinst_${PN}() {
#/bin/sh
if test -e "/etc/runit/ds485d/supervise/stat"; then
    if test `cat /etc/runit/ds485d/supervise/stat` = "run"; then
        echo "Restarting dS485d..."
        sv restart ds485d
    fi
fi
}

