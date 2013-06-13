PRINC = "3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://defconfig"

do_install_prepend() {
}
