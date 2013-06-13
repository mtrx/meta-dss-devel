DESCRIPTION = "Metapackage for system add-ons"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"

PR = "r5"

inherit task

SYSTEM_ADD_ONS = "\
    system-addon-presence-simulator \
    system-addon-scene-responder \
    system-addon-timed-events \
    system-addon-user-defined-actions \
    system-addon-metering \
"

RDEPENDS_${PN} = "${SYSTEM_ADD_ONS}"

do_install() {
    install -d ${D}/var/lib/opkg
    rm -f ${D}/var/lib/opkg/addon-replacements
    for f in ${SYSTEM_ADD_ONS}; do
        echo ${f:13} >> ${D}/var/lib/opkg/addon-replacements
    done
}
