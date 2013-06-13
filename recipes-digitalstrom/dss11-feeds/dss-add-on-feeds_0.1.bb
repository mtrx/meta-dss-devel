# This recipe is heavily based on angstrom-feed-configs.bb

DESCRIPTION = "Configuration files for the add-ons repository"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"
RRECOMMENDS_${PN} += "opkg opkg2json"

SRC_URI = "file://opkg.conf file://addon.sh file://addon2json.sh"
PR = "r8"

PACKAGE_ARCH = "all"

DSS_ADDON_FEED_BASEPATH ?= "feeds/dss-add-ons-testing/ipk/"
DSS_ADDON_FEED_URI ?= "http://feedback.aizo.com"

OPKG_ADD_ONS_CONF_DIR = "opkg-add-ons"


do_compile() {
	mkdir -p ${S}/${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}

	rm ${S}/${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}/arch.conf || true
	ipkgarchs="${PACKAGE_ARCHS}"
	priority=1
	for arch in $ipkgarchs; do 
		echo "arch $arch $priority" >> ${S}/${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}/arch.conf
		priority=$(expr $priority + 5)
	done

	echo "src/gz add-ons ${DSS_ADDON_FEED_URI}/${DSS_ADDON_FEED_BASEPATH}all" > ${S}/${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}/add-ons-feed.conf
}

do_install () {
	install -d ${D}${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}
	install -m 0644  ${S}/${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}/* ${D}${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}
    install -m 0644  ${WORKDIR}/opkg.conf ${D}${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}
    install -d ${D}${bindir}/
    install -m 0755 ${WORKDIR}/addon.sh ${D}${bindir}/addon
    install -m 0755 ${WORKDIR}/addon2json.sh ${D}${bindir}/addon2json
}

FILES_${PN} += " ${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}/add-ons-feed.conf \
                 ${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}/arch.conf \
                 ${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}/opkg.conf \
                 ${D}${bindir}/addon \
               "

CONFFILES_${PN} += "${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}/add-ons-feed.conf \
					${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}/arch.conf \
                    ${sysconfdir}/${OPKG_ADD_ONS_CONF_DIR}/opkg.conf \
					"

pkg_preinst_${PN} () {
#!/bin/sh
    if [ -d /usr/lib/opkg-add-ons/alternatives ]; then
        # if it's already a link, then migration has been done and we can
        # safely remove it
        if [ -L /usr/lib/opkg-add-ons/alternatives ]; then
            echo "Removing obsolete /usr/lib/opkg-add-ons/alternatives link"
            rm /usr/lib/opkg-add-ons/alternatives
        else
            echo "Migrating opkg-add-ons alternatives"
            mkdir -p /var/lib/opkg-add-ons/alternatives
            if [ "$(ls -A /usr/lib/opkg-add-ons/alternatives)" ]; then
                mv /usr/lib/opkg-add-ons/alternatives/* /var/lib/opkg-add-ons/alternatives
            fi
            rmdir /usr/lib/opkg-add-ons/alternatives
            ln -s /var/lib/opkg-add-ons/alternatives /usr/lib/opkg-add-ons/alternatives
        fi
    fi
    if [ -d  /usr/lib/opkg-add-ons/info ]; then
        if [ -L /usr/lib/opkg-add-ons/info ]; then
            echo "Removing obsolete /usr/lib/opkg-add-ons/info"
            rm /usr/lib/opkg-add-ons/info
        else
            echo "Migrating opkg-add-ons info"
            mkdir -p /var/lib/opkg-add-ons/info
            if [ "$(ls -A /usr/lib/opkg-add-ons/info)" ]; then
                mv /usr/lib/opkg-add-ons/info/* /var/lib/opkg-add-ons/info
            fi
            rmdir /usr/lib/opkg-add-ons/info
            ln -s /var/lib/opkg-add-ons/info /usr/lib/opkg-add-ons/info
        fi
    fi
    if [ -d  /usr/lib/opkg-add-ons/lists ]; then
        if [ -L /usr/lib/opkg-add-ons/lists ]; then
            echo "Removing obsolete /usr/lib/opkg-add-ons/lists"
        else
            echo "Migrating opkg-add-ons lists"
            mkdir -p /var/lib/opkg-add-ons/lists
            if [ "$(ls -A /usr/lib/opkg-add-ons/lists)" ]; then
                mv /var/lib/opkg-add-ons/lists/* /usr/lib/opkg-add-ons/lists
            fi
            rmdir /usr/lib/opkg-add-ons/lists
            ln -s /var/lib/opkg-add-ons/lists /usr/lib/opkg-add-ons/lists
        fi
    fi
    if [ -f /usr/lib/opkg-add-ons/status ]; then
        if [ -L /usr/lib/opkg-add-ons/status ]; then
            echo "Removing obsolete /usr/lib/opkg-add-ons/status"
        else
            echo "Migrating opkg-add-ons status"
            mv /usr/lib/opkg-add-ons/status /var/lib/opkg-add-ons/status
            ln -s /var/lib/opkg-add-ons/status /usr/lib/opkg-add-ons/status
        fi
    fi
}

