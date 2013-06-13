DEPENDS = "glib-2.0 fontconfig freetype zlib virtual/libiconv cairo"
EXTRA_OECONF += "--enable-cairo --without-x --disable-xlib"
INC_PR = "r4"

FILES_${PN} += "${sysconfdir}/pango"

do_install_append() {
    install -d 0755 ${D}${sysconfdir}/pango
}

