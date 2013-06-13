PRINC = "1"

PACKAGES := "${@oe_filter_out('libext2fs', '${PACKAGES}', d)}"
FILES_${PN} =+ "${libdir}/e2initrd_helper ${libdir}/libext2fs.so.*"

