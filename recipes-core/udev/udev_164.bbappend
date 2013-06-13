FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

PRINC = "4"

RRECOMMENDS_${PN} = "udev-extraconf usbutils-ids pciutils-ids"
