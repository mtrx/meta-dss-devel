PRINC = "1"

PACKAGES := "${PN}-utils  ${@oe_filter_out('${PN}-utils', '${PACKAGES}', d)}"

FILES_${PN}-utils = "${bindir}/xmllint ${bindir}/xmlcatalog"

