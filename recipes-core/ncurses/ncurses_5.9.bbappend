PRINC="2"

PACKAGES := "${@oe_filter_out('${PN}-terminfo-base', '${PACKAGES}', d)}"

FILES_${PN} += "${sysconfdir}/terminfo"
