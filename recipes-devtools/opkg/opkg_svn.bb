require opkg.inc

PROVIDES += "virtual/update-alternatives"
RPROVIDES_update-alternatives-cworth += "update-alternatives"
RCONFLICTS_update-alternatives-cworth = "update-alternatives-dpkg"
RDEPENDS_${PN} = "${DISTRO_UPDATE_ALTERNATIVES} opkg-config-base opkg-collateral"
RDEPENDS_${PN}_virtclass-native = ""
RDEPENDS_${PN}_virtclass-nativesdk = ""
PACKAGE_ARCH_update-alternatives-cworth = "all"
RREPLACES_${PN} = "opkg-nogpg"

# not really a clean solution with the notice patch, but otherwise rootfs
# generation on the host fails
SRC_URI = " \
    svn://opkg.googlecode.com/svn;module=trunk;proto=http \
    file://0001-add-opkg_compare_versions-function.patch \
    file://0002-Ensure-we-use-the-uname-gname-fields-when-extracting.patch \
    file://0003-Fix-dependency-issues-for-preinst-scripts.patch \
    file://0004-Failed-postinst-script-is-not-fatal-with-conf-offlin.patch \
    file://0005-Do-not-read-etc-opkg-.conf-if-f-is-specified.patch \
    file://0006-detect-circular-dependencies.patch \
    file://0007-merge-newpkg-provides-even-when-oldpkg-provides-exis.patch \
    file://0008-select_higher_version.patch \
    file://0009-pkg_depends-fix-version-constraints.patch \
    file://0010-pkg_depends-fix-version_constraints_satisfied.patch \
    file://0011-opkg-no-sync-offline.patch \
    file://separate_libdirs.patch \
    file://print_homepage.patch \
    file://fix_tags.patch \
    file://notice_instead_of_error.patch \
    file://d0001-opkg_message-add-methods-to-collect-the-error-causes.patch \
    file://d0002-add-opkg_error_cause-to-an-number-of-methods.patch \
    file://d0003-only-return-unspecified-error-when-no-other-error-wa.patch \
    file://d0004-specify-error_cause-if-a-package-is-not-upgraded.patch \
    file://dlsize.patch \
"

S = "${WORKDIR}/trunk"

SRCREV = "635"
PV = "0.1.8+svnr${SRCPV}"
PR = "r16"

PACKAGES =+ "libopkg${PKGSUFFIX}-dev libopkg${PKGSUFFIX} update-alternatives-cworth${PKGSUFFIX}"

FILES_update-alternatives-cworth${PKGSUFFIX} = "${bindir}/update-alternatives"
FILES_libopkg${PKGSUFFIX}-dev = "${includedir}/*.h ${libdir}/*.so"
FILES_libopkg${PKGSUFFIX}-staticdev = "${libdir}/*.a ${libdir}/*.la"
FILES_libopkg${PKGSUFFIX} = "${libdir}/*.so.* ${localstatedir}/lib/opkg/* ${libdir}/opkg"

# We need to create the lock directory
do_install_append() {
    install -d ${D}${localstatedir}/lib/opkg
}

pkg_preinst_${PN} () {
#!/bin/sh
    if [ -d /usr/lib/opkg/alternatives ]; then
        # if it's already a link, then migration has been done and we can
        # safely remove it
        if [ -L /usr/lib/opkg/alternatives ]; then
            echo "Removing obsolete /usr/lib/opkg/alternatives link"
            rm /usr/lib/opkg/alternatives
        else
            echo "Migrating opkg alternatives"
            mkdir -p /var/lib/opkg/alternatives
            if [ "$(ls -A /usr/lib/opkg/alternatives)" ]; then
                mv /usr/lib/opkg/alternatives/* /var/lib/opkg/alternatives
            fi
            rmdir /usr/lib/opkg/alternatives
            ln -s /var/lib/opkg/alternatives /usr/lib/opkg/alternatives
        fi
    fi
    if [ -d  /usr/lib/opkg/info ]; then
        if [ -L /usr/lib/opkg/info ]; then
            echo "Removing obsolete /usr/lib/opkg/info"
            rm /usr/lib/opkg/info
        else
            echo "Migrating opkg info"
            mkdir -p /var/lib/opkg/info
            if [ "$(ls -A /usr/lib/opkg/info)" ]; then
                mv /usr/lib/opkg/info/* /var/lib/opkg/info
            fi
            rmdir /usr/lib/opkg/info
            ln -s /var/lib/opkg/info /usr/lib/opkg/info
        fi
    fi
    if [ -d  /usr/lib/opkg/lists ]; then
        if [ -L /usr/lib/opkg/lists ]; then
            echo "Removing obsolete /usr/lib/opkg/lists"
        else
            echo "Migrating opkg lists"
            mkdir -p /var/lib/opkg/lists
            if [ "$(ls -A /usr/lib/opkg/lists)" ]; then
                mv /var/lib/opkg/lists/* /usr/lib/opkg/lists
            fi
            rmdir /usr/lib/opkg/lists
            ln -s /var/lib/opkg/lists /usr/lib/opkg/lists
        fi
    fi

    if [ -f /usr/lib/opkg/status ]; then
        if [ -L /usr/lib/opkg/status ]; then
            echo "Removing obsolete /usr/lib/opkg/status"
        else
            echo "Migrating opkg status"
            mv /usr/lib/opkg/status /var/lib/opkg/status
            ln -s /var/lib/opkg/status /usr/lib/opkg/status
        fi
    fi

# handle add-ons configuration
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

pkg_postinst_${PN} () {
#!/bin/sh
if [ "x$D" != "x" ]; then
	install -d $D${sysconfdir}/rcS.d
	# this happens at S98 where our good 'ole packages script used to run
	echo "#!/bin/sh
opkg-cl configure
rm -f /${sysconfdir}/rcS.d/S${POSTINSTALL_INITPOSITION}configure
" > $D${sysconfdir}/rcS.d/S${POSTINSTALL_INITPOSITION}configure
	chmod 0755 $D${sysconfdir}/rcS.d/S${POSTINSTALL_INITPOSITION}configure
fi

update-alternatives --install ${bindir}/opkg opkg ${bindir}/opkg-cl 100
}

pkg_postrm_${PN} () {
#!/bin/sh
update-alternatives --remove opkg ${bindir}/opkg-cl
}

