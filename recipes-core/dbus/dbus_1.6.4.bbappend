PRINC = "1"

# do not create a static dir vor /var/run - it will go to volatile/run
do_install() {
    autotools_do_install

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/dbus-1.init ${D}${sysconfdir}/init.d/dbus-1

    install -d ${D}${sysconfdir}/default/volatiles
    echo "d messagebus messagebus 0755 ${localstatedir}/run/dbus none" \
         > ${D}${sysconfdir}/default/volatiles/99_dbus


    mkdir -p ${D}${localstatedir}/lib/dbus

    chmod 4754 ${D}${libexecdir}/dbus-daemon-launch-helper

    # Remove Red Hat initscript
    rm -rf ${D}${sysconfdir}/rc.d
    # remove /var/run/dbus since it breaks image generation (volatile setup)
    rm -rf ${D}${localstatedir}/run
}

