# This recipe is heavily based on angstrom-feed-configs.bb

DESCRIPTION = "Configuration files for dss11 development package repository"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"
RRECOMMENDS_${PN} += "opkg"

PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DSS11_FEED_BASEPATH ?= "feeds/dss11-${RELEASE_TYPE}-eglibc/ipk/"
DSS11_FEED_URI ?= "http://update.aizo.com"


do_compile() {
	mkdir -p ${S}/${sysconfdir}/opkg

	rm ${S}/${sysconfdir}/opkg/arch-${RELEASE_TYPE}.conf || true
	ipkgarchs="${PACKAGE_ARCHS}"
	priority=1
	for arch in $ipkgarchs; do 
		echo "arch $arch $priority" >> ${S}/${sysconfdir}/opkg/arch-${RELEASE_TYPE}.conf
		priority=$(expr $priority + 5)
	done

	for feed in base ; do
		  echo "src/gz ${feed}-${RELEASE_TYPE} ${DSS11_FEED_URI}/${DSS11_FEED_BASEPATH}${FEED_ARCH}" > ${S}/${sysconfdir}/opkg/${feed}-${RELEASE_TYPE}-feed.conf
	done

	echo "src/gz ${MACHINE_ARCH}-${RELEASE_TYPE} ${DSS11_FEED_URI}/${DSS11_FEED_BASEPATH}${MACHINE_ARCH}" >  ${S}/${sysconfdir}/opkg/${MACHINE_ARCH}-${RELEASE_TYPE}-feed.conf
	echo "src/gz no-arch-${RELEASE_TYPE} ${DSS11_FEED_URI}/${DSS11_FEED_BASEPATH}all" > ${S}/${sysconfdir}/opkg/noarch-${RELEASE_TYPE}-feed.conf
}


do_install () {
	install -d ${D}${sysconfdir}/opkg
	install -m 0644  ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg/
}

FILES_${PN} = "${sysconfdir}/opkg/base-${RELEASE_TYPE}-feed.conf \
					${sysconfdir}/opkg/${MACHINE_ARCH}-${RELEASE_TYPE}-feed.conf \
					${sysconfdir}/opkg/noarch-${RELEASE_TYPE}-feed.conf \
                    ${sysconfdir}/opkg/arch-${RELEASE_TYPE}.conf \
					"

CONFFILES_${PN} += "${sysconfdir}/opkg/base-${RELEASE_TYPE}-feed.conf \
					${sysconfdir}/opkg/${MACHINE_ARCH}-${RELEASE_TYPE}-feed.conf \
					${sysconfdir}/opkg/noarch-${RELEASE_TYPE}-feed.conf \
                    ${sysconfdir}/opkg/arch-${RELEASE_TYPE}.conf \
					"

