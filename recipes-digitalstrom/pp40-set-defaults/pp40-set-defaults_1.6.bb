DESCRIPTION = "tool to update defaults in dSM and dSC"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
HOMEPAGE = "http://gitorious.newtechgroup.local/dsslinux/pp40-set-defaults"
DEPENDS = "libdsm-api"
PR="r1"

SRC_URI[md5sum] = "5ca28a7edadd80ab0181bb64c42aa3d8"
SRC_URI[sha256sum] = "9f245161ae8bf2b6d9ff197d2370258e49497c37a607e47c0a97aeaa9b70cbd6"

SRC_URI="http://developer.digitalstrom.org/download/utilities/pp40-set-defaults-${PV}.tar.gz"

inherit autotools
