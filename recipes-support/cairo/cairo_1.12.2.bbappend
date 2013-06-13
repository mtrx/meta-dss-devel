DEPENDS = "libpng freetype fontconfig glib-2.0 pixman"
EXTRA_OECONF += "--enable-xlib=no \
  --enable-xlib-xrender=no \
  --enable-xcb=no \
  --without-x"
PR = "${INC_PR}.1"
INC_PR = "r3"
