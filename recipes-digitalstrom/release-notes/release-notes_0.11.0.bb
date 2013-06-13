# release notes webpage
LICENSE = "GPL-3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"
DESCRIPTION = "image release notes"

PR = "r1"

SRC_URI = "file://notes_de_DE.html \
           file://notes_fr_CH.html \
           file://notes_en_US.html \
           file://notes_nl_NL.html \
           file://notes_tr_TR.html"

do_compile[noexec] = "1"

do_install() {
    install -m 0755 -d ${D}/www/pages/releasenotes
    install ${WORKDIR}/notes_de_DE.html  ${D}/www/pages/releasenotes/notes_de_DE.html
    install ${WORKDIR}/notes_fr_CH.html  ${D}/www/pages/releasenotes/notes_fr_CH.html
    install ${WORKDIR}/notes_en_US.html  ${D}/www/pages/releasenotes/notes_en_US.html
    install ${WORKDIR}/notes_nl_NL.html  ${D}/www/pages/releasenotes/notes_nl_NL.html
    install ${WORKDIR}/notes_tr_TR.html  ${D}/www/pages/releasenotes/notes_tr_TR.html
}

FILES_${PN} = "/www/pages/releasenotes/*"

PACKAGE_ARCH_${PN} = "all"

