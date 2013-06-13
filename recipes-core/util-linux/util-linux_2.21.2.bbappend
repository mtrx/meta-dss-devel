PRINC="3"

python () {
    packages = (d.getVar("PACKAGES", False) or "").split()
    for package in packages:
        pkg = package.replace("util-linux", "util-linux-ng")
        if package == "util-linux-blkid":
            pkg = pkg + " e2fsprogs-blkid"
        d.setVar("RREPLACES_" + package, pkg)
        d.setVar("RCONFLICTS_" + package, pkg)
}

FILES_util-linux-swaponoff = "${base_sbindir}/swapon.${PN} ${base_sbindir}/swapoff.${PN}"

PACKAGES =+ "util-linux-flock"

FILES_util-linux-flock = "${bindir}/flock.${PN}"

pkg_postinst_${PN} () {
    update-alternatives --install ${base_bindir}/dmesg dmesg dmesg.${PN} 100
    update-alternatives --install ${base_bindir}/kill kill kill.${PN} 100
    update-alternatives --install ${base_bindir}/more more more.${PN} 100
    update-alternatives --install ${base_sbindir}/mkswap mkswap mkswap.${PN} 100
    update-alternatives --install ${base_sbindir}/blockdev blockdev blockdev.${PN} 100
    test -x ${base_sbindir}/pivot_root.${PN} && \
    update-alternatives --install ${base_sbindir}/pivot_root pivot_root pivot_root.${PN} 100
#   update-alternatives --install ${base_sbindir}/sln sln sln.${PN} 100
    update-alternatives --install ${base_sbindir}/mkfs.minix mkfs.minix mkfs.minix.${PN} 100
    update-alternatives --install ${bindir}/hexdump hexdump hexdump.${PN} 100
    update-alternatives --install ${bindir}/last last last.${PN} 100
    update-alternatives --install ${bindir}/logger logger logger.${PN} 100
    update-alternatives --install ${bindir}/mesg mesg mesg.${PN} 100
    update-alternatives --install ${bindir}/renice renice renice.${PN} 100
    update-alternatives --install ${bindir}/wall wall wall.${PN} 100
    update-alternatives --install ${bindir}/setsid setsid setsid.${PN} 100
    update-alternatives --install ${bindir}/chrt chrt chrt.${PN} 100

    # There seems to be problem, atleast on nslu2, with these, untill they are
    # fixed the busybox ones have higher priority
    update-alternatives --install ${base_sbindir}/hwclock hwclock hwclock.${PN} 10
    update-alternatives --install ${base_sbindir}/shutdown shutdown shutdown.${PN} 10
    update-alternatives --install ${base_sbindir}/reboot reboot reboot.${PN} 10
    update-alternatives --install ${base_sbindir}/halt halt halt.${PN} 10
}

pkg_prerm_${PN} () {
    test -x ${base_sbindir}/pivot_root.${PN} && \
    update-alternatives --remove pivot_root pivot_root.${PN}
    update-alternatives --remove dmesg dmesg.${PN}
    update-alternatives --remove kill kill.${PN}
    update-alternatives --remove more more.${PN}
    update-alternatives --remove halt halt.${PN}
    update-alternatives --remove hwclock hwclock.${PN}
    update-alternatives --remove mkswap mkswap.${PN}
    update-alternatives --remove blockdev blockdev.${PN}
    update-alternatives --remove reboot reboot.${PN}
    update-alternatives --remove shutdown shutdown.${PN}
#   update-alternatives --remove sln sln.${PN}
    update-alternatives --remove mkfs.minix mkfs.minix.${PN}
    update-alternatives --remove hexdump hexdump.${PN}
    update-alternatives --remove last last.${PN}
    update-alternatives --remove logger logger.${PN}
    update-alternatives --remove mesg mesg.${PN}
    update-alternatives --remove renice renice.${PN}
    update-alternatives --remove wall wall.${PN}
    update-alternatives --remove setsid setsid.${PN}
    update-alternatives --remove chrt chrt.${PN}
}

pkg_postinst_util-linux-flock () {
    update-alternatives --install ${bindir}/flock flock flock.${PN} 100
}

pkg_prerm_util-linux-flock () {
    update-alternatives --remove flock flock.${PN}
}
