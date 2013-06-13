DESCRIPTION = "SD-Card maintenance and startup script"
SECTION = "base"
DEPENDS = "makedevs"
RDEPENDS_${PN} = "makedevs util-linux-blkid"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"
PR = "r4"

SRC_URI = "file://sdcard \
           file://e2fsck.conf \
"

inherit update-rc.d

INITSCRIPT_NAME = "sdcard"
INITSCRIPT_PARAMS = "start 39 S ."

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/sdcard ${D}${sysconfdir}/init.d/sdcard
    chmod 0755 ${D}${sysconfdir}/init.d/sdcard
    install -m 0755 ${WORKDIR}/e2fsck.conf ${D}${sysconfdir}/
}

pkg_preinst_${PN}() {
#/bin/sh
update-rc.d -f sdcard remove
}

pkg_postinst_${PN}() {
#/bin/sh

grep mmcblk /etc/udev/mount.blacklist || \
   echo "/dev/mmcblk" >> /etc/udev/mount.blacklist

/etc/init.d/sdcard test
if [ $? -eq 255 ]; then
   echo "$0: changed external storage - reboot required!"
   (
      while true; do
         UID=`fuser /usr/lib/opkg/lock 2>/dev/null`
         [ "$UID" -eq "" ] && break
         echo "$0: reboot delayed by opkg [$UID]"
         sleep 1
      done
      echo "$0: initiate reboot now"
      /sbin/reboot
   )&
fi
}
