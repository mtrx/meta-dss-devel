# create self signed certificate
DESCRIPTION = "Helper script to create a self signed certificate"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PR = "r1"

RDEPENDS_${PN} = "openssl openssl-misc"

SRC_URI = "file://create-cert"

do_install() {
    install -m 0755 -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/create-cert ${D}${bindir}
}

PACKAGE_ARCH_${PN} = "all"

