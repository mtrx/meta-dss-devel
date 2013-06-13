DESCRIPTION = "LUA4JSON"
SECTION = "libs"
LIC_FILES_CHKSUM="file://doc/LICENCE.txt;md5=0989eedf014c09bc1415a54cbc1086d5"
LICENSE = "MIT"

BV = "0.9.30"
PV = "${BV}"
PR = "r0"

DEPENDS = "lua5.1"

SRC_URI="http://luaforge.net/frs/download.php/4184/JSON4Lua-${PV}.zip"

SRC_URI[md5sum] = "d5f4c7515857868f6e98211b9cc07fb6"
SRC_URI[sha256sum] = "a78cecda3902dffb1a3bf200eeda58a5df893970e055d6940adda01b362d8e51"

S = "${WORKDIR}/JSON4Lua-${PV}"

do_install() {
	mkdir -pv ${D}/${datadir}/lua/5.1
	install -v -m 644 json/* ${D}/${datadir}/lua/5.1
}

FILES_${PN} += "${datadir}/lua"
