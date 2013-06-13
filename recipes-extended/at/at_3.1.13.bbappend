# original at recipe does not configure the init scripts

PRINC = "2"

INITSCRIPT_NAME = "atd"
INITSCRIPT_PARAMS = "start 39 S ."

inherit update-rc.d

# not sure who or what installs the S99at link...
pkg_preinst_${PN}() {
    update-rc.d -f atd remove
}

pkg_postinst_${PN}() {
    update-rc.d at remove
}
