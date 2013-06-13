DESCRIPTION = "lua environment settings"
SECTION = "base"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r1"

SRC_URI = "file://lua.sh"

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/profile.d
    install -m 0755 ${WORKDIR}/lua.sh ${D}${sysconfdir}/profile.d/lua.sh
}
