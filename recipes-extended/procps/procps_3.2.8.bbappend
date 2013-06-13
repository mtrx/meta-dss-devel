# sysctl.conf will be installed by base files, procps is not in the image by
# default

PRINC="1"

CONFFILES_${PN} = ""

do_install_append () {
    rm ${D}${sysconfdir}/sysctl.conf
}
