# UPnP beacon utility
DESCRIPTION = "Utility to announce presence of a device via UPnP"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
HOMEPAGE = "http://www.digitalstrom.org/"
DEPENDS = "libupnp libconfig util-linux"
PR="r7"

SRC_URI="http://developer.digitalstrom.org/releases/tools/upnpbeacon-${PV}.tar.gz \
         file://upnpbeacon.cfg \
         file://upnpbeacon.ifup \
         file://upnpbeacon.init \
         file://upnpbeacon.udhcpc"

INITSCRIPT_NAME = "upnpbeacon"
INITSCRIPT_PARAMS = "defaults 99"

inherit autotools update-rc.d

CONFFILES_${PN} = "${sysconfdir}/upnpbeacon.cfg ${sysconfdir}/upnpbeacon.udn"

SRC_URI[md5sum] = "47012ad96340081ba40f5ea76b68369c"
SRC_URI[sha256sum] = "50cfc60a9329eb3742fe78382f4559071748c188f3d1d6f8693de8f73be8459b"

do_install_append() {
    install -m 0755 -d ${D}${sysconfdir}
    install ${WORKDIR}/upnpbeacon.cfg ${D}${sysconfdir}/upnpbeacon.cfg
    touch ${D}${sysconfdir}/upnpbeacon.udn

    install -D -m 0755 ${WORKDIR}/upnpbeacon.init ${D}${sysconfdir}/init.d/upnpbeacon
    install -D -m 0755 ${WORKDIR}/upnpbeacon.ifup ${D}${sysconfdir}/network/if-up.d/upnpbeacon
    install -D -m 0755 ${WORKDIR}/upnpbeacon.udhcpc ${D}${sysconfdir}/udhcpc.d/99upnpbeacon
}

