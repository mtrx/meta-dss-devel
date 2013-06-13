FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "5"

SRC_URI += "file://Copyright"

LIC_FILES_CHKSUM="file://Copyright;md5=751419260aa954499f7abaabaa882bbe"
RDEPENDS_${PN} = "${DISTRO_UPDATE_ALTERNATIVES}"

RREPLACES_${PN} = "sysvinit-utils"
RCONFLICTS_${PN} = "sysvinit-utils"

FILES_${PN} += "${sysconfdir}/default/*"
do_install_append() {
    install -d ${D}${sysconfdir}/default
    echo "BOOTLOGD_ENABLE=no" > ${D}${sysconfdir}/default/bootlogd
}

# something is messing around with COPYING in the background, on my system
# it seemed to get relineked, which generated a different checksum
do_configure_append() {
    cp ${WORKDIR}/Copyright ${S}/
}
