FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PRINC = "5"

DEPENDS += "openssl"
RDEPENDS += "ca-certificates runit"

SRC_URI += "file://msmtprc file://msmtp.logrotate file://sendmail file://mailq.sh file://mailq.run"

EXTRA_OECONF += "\
  --with-libgnutls-prefix=${STAGING_LIBDIR}/.. \
  --with-libssl-prefix=${STAGING_LIBDIR}/.. \
    "
CONFFILES_${PN} = "${sysconfdir}/msmtprc"

do_install_append() {
    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0755 ${WORKDIR}/msmtprc ${D}${sysconfdir}
    install ${WORKDIR}/msmtp.logrotate ${D}${sysconfdir}/logrotate.d/msmtp
    install -d ${D}/${sbindir}
    install -m 0755 ${WORKDIR}/sendmail ${D}/${sbindir}/sendmail.wrapper
    install -m 0755 ${WORKDIR}/mailq.sh ${D}/${sbindir}/mailq
    install -d ${D}${sysconfdir}/runit/mailq
    install -m 0755 ${WORKDIR}/mailq.run ${D}${sysconfdir}/runit/mailq/run
}

ALTERNATIVE_PATH = "${sbindir}/sendmail.wrapper"
