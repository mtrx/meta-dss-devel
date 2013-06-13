# in case the correct mdns configuration is already in /etc/nsswitch.conf
# (which is the case since we fixed it in our old image), the new libnss-mdns
# package will return errors in postinst/prerm scripts.
#
# we'll fix it by hardcoding the return value

PRINC = "1"

pkg_postinst_${PN}_append () {
    exit 0
}

pkg_prerm_${PN}_append () {
    exit 0
}

