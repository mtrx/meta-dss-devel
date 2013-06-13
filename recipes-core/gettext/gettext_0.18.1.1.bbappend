EXTRA_OECONF += "--enable-libasprintf"

PACKAGES += "gettext-libruntime"
FILES_gettext-libruntime = "${libdir}/libasprintf${SOLIBS}"
FILES_gettext-runtime-dev = "${includedir}/autosprintf.h"
FILES_gettext-runtime-staticdev = "${libdir}/libasprintf$.a"
FILES_gettext-runtime = "${bindir}/gettext \
                         ${bindir}/ngettext \
                         ${bindir}/envsubst \
                         ${bindir}/gettext.sh \
                         ${libdir}/GNU.Gettext.dll \
                         "
RDEPENDS_gettext-runtime-staticdev = "gettext-runtime-dev (= ${EXTENDPKGV})"
