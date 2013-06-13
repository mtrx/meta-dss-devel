#!/bin/sh
OPKG_CONF_DIR="/etc/opkg-add-ons" OPKG_LIB_DIR="/var/lib/opkg-add-ons" opkg $@
RETCODE="$?"

if [ "$1" == "remove" ]; then
    if [ -f "/usr/share/dss/data/savedprops/$2.xml" ]; then
        rm -f /usr/share/dss/data/savedprops/$2.xml
    fi
fi

exit $RETCODE