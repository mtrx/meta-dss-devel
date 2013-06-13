PRINC="1"

do_install_append() {
    if [ "${TCLIBC}"x = "eglibc"x ] ; then
        install -d ${D}${datadir}/zoneinfo
        cp -pP "${S}/zone.tab" ${D}${datadir}/zoneinfo/
        cp -pP "${S}/iso3166.tab" ${D}${datadir}/zoneinfo
    fi
}
