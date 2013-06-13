# dSS11 button daemon
DESCRIPTION = "dSS11 button daemon"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
HOMEPAGE = "http://gitorious.digitalstrom.org/dss-misc/dss11-buttond"
RDEPENDS_${PN} = "dss11-utils"
PR="r3"

SRC_URI[md5sum] = "d761f0b37ddfdefb672da944aea7185b"
SRC_URI[sha256sum] = "597b244d7cf11e717e5ef777332fa8d4092835b7420463273834b082430b4534"

SRC_URI="http://developer.digitalstrom.org/download/dss11-buttond/dss11-buttond-${PV}.tar.gz \
	file://init \
	file://dss11-buttond-proxy.oe \
"

INITSCRIPT_NAME = "dss11-buttond"
INITSCRIPT_PARAMS = "defaults 65"

inherit autotools update-rc.d

do_install_append() {
    install -d ${D}${bindir}
    install -m 755 ${WORKDIR}/dss11-buttond-proxy.oe ${D}${bindir}/dss11-buttond-proxy

    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/init.d
    install -D -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/dss11-buttond

    install -d ${D}${sysconfdir}/dss11-buttond-proxy.d
}
