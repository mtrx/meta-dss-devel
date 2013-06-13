DESCRIPTION = "Metapackage containing useful lua extensions"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"

PR = "r2"

inherit task

RDEPENDS_${PN} = "\
    lua5.1 \
    lua-profile \
    luabitop \
    luasocket \
    luajson \
"
