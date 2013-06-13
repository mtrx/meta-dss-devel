FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://sysctl.conf"

PRINC = "45"

# undo whatever angstrom's bbapend is doing here
dirs755 := "${@oe_filter_out('${localstatedir}/cache', '${dirs755}', d)}"
dirs755 := "${@oe_filter_out('${localstatedir}/run', '${dirs755}', d)}"
dirs755 := "${@oe_filter_out('${localstatedir}/lock', '${dirs755}', d)}"
dirs755 := "${@oe_filter_out('${localstatedir}/lock/subsys', '${dirs755}', d)}"
dirs755 := "${@oe_filter_out('${localstatedir}/tmp', '${dirs755}', d)}"
dirs755 := "${@oe_filter_out('${localstatedir}/volatile/tmp', '${dirs755}', d)}"
dirs755 := "${@oe_filter_out('/run', '${dirs755}', d)}"
dirs755 += "${localstatedir}/lib/dsa /www/pages/add-ons /usr/share/dss"

volatiles = "cache lock tmp run"

hostname = "dSS"

do_install_angstromissue_append() {
    echo ${hostname} > ${D}${sysconfdir}/hostname
}

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/sysctl.conf ${D}${sysconfdir}/sysctl.conf
}

# overwrite angstrom's postinst which would rmeove the /var/run link
pkg_postinst_${PN} () {
#!/bin/sh
if [ "x$D" != "x" ]; then
        exit 1
fi

cat /proc/mounts | grep /media/card | grep sync >/dev/null 2>&1
if [[ $? -eq 0 ]]; then
     mount -o remount,async /media/card/ >/dev/null 2>&1 ||
        echo "" >/dev/null 2>&1
fi

if [[ -x /sbin/sysctl ]] && [[ -f /etc/sysctl.conf ]]; then
    /sbin/sysctl -p /etc/sysctl.conf
fi

# check if previously installed base-files had different sysctl settings
if [ -f /tmp/kernel_min_free_kbytes ]; then
    currentval=0
    eval `cat /tmp/kernel_min_free_kbytes | sed -e 's, ,,g' -e 's,\.,_,g'`
    vm_min_free_kbytes=$currentval
    if [ -f /etc/sysctl.conf ]; then
        eval `cat /etc/sysctl.conf | sed -e 's, ,,g' -e 's,\.,_,g'`
    fi
    if [ ${currentval} -ne ${vm_min_free_kbytes} ]; then
        echo "$0: sysctl parameter changes - reboot required!"
        touch /var/lock/rebootafterupdate
    fi
fi
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "currentval=`cat /proc/sys/vm/min_free_kbytes`" > /tmp/kernel_min_free_kbytes
}

PACKAGE_ARCH="${MACHINE_ARCH}"
