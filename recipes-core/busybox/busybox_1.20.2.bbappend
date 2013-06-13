# tune syslog config to write to file instead of buffer
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PRINC = "1"

CONFFILES_${PN}-syslog = ""
#RDEPENDS_${PN} += "${DISTRO_UPDATE_ALTERNATIVES}"
#RDEPENDS_${PN}-mountall += "${DISTRO_UPDATE_ALTERNATIVES}"

SRC_URI += "file://ifplugd.conf \
            file://ifplugd.action \
            file://ifplugd.init \
            file://kill-udhcpc \
            file://udhcpc-getlease \
	    file://0001-smemcap.c-skip-unavailable-files-instead-of-dying.patch \
            "

RRECOMMENDS_${PN} += "${PN}-ifplugd"
RPROVIDES_${PN}-ifplugd = "netplug"
RREPLACES_${PN}-ifplugd = "netplug"
RCONFLICTS_${PN}-ifplugd = "netplug"

PACKAGES =+ "${PN}-ifplugd"

FILES_${PN}-ifplugd = "${sysconfdir}/ifplugd/* ${sysconfdir}/init.d/busybox-ifplugd"
FILES_${PN}-udhcpc += "${sysconfdir}/network/if-down.d/kill-udhcpc ${sbindir}/udhcpc-getlease"
INITSCRIPT_PACKAGES += "${PN}-ifplugd"
INITSCRIPT_NAME_${PN}-ifplugd = "${PN}-ifplugd"
INITSCRIPT_PARAMS_${PN}-ifplugd = "defaults 85"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/ifplugd
    install -d ${D}${sysconfdir}/network/if-down.d
    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/ifplugd.init ${D}${sysconfdir}/init.d/${PN}-ifplugd
    install -m 0755 ${WORKDIR}/ifplugd.action ${D}${sysconfdir}/ifplugd/ifplugd.action
    install -m 0644 ${WORKDIR}/ifplugd.conf ${D}${sysconfdir}/ifplugd/ifplugd.conf
    install -m 0755 ${WORKDIR}/kill-udhcpc ${D}${sysconfdir}/network/if-down.d/kill-udhcpc
    install -m 0755 ${WORKDIR}/udhcpc-getlease ${D}${sbindir}/udhcpc-getlease
}
