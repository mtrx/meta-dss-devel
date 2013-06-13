DESCRIPTION = "opkg configuration files"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r4"
SRC_URI = "file://opkg.conf.comments \
	   file://lists \
	   file://dest \
	   file://src "

do_compile () {
	cat ${WORKDIR}/opkg.conf.comments >${WORKDIR}/opkg.conf
	cat ${WORKDIR}/src	>>${WORKDIR}/opkg.conf
	cat ${WORKDIR}/dest	>>${WORKDIR}/opkg.conf
	cat ${WORKDIR}/lists	>>${WORKDIR}/opkg.conf
	echo "option tmp_dir ${localstatedir}/lib/opkg/tmp" >>${WORKDIR}/opkg.conf
}

do_install () {
	install -d ${D}${sysconfdir}/opkg
	install -d ${D}${localstatedir}/lib/opkg/tmp
	install -m 0644 ${WORKDIR}/opkg.conf ${D}${sysconfdir}/opkg/opkg.conf
}
