PRINC = "1"

do_install_append() {
    echo "/dev/mmcblk" >> ${D}${sysconfdir}/udev/mount.blacklist
}
