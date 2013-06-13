DESCRIPTION = "An alternative to sprocketize"
HOMEPAGE = "http://blog.angeloff.name/post/3465035915/yoke-a-drop-in-quick-and-dirty-alternative-to"
LICENSE = "unknown"
LIC_FILES_CHKSUM = "file://${WORKDIR}/license;md5=7ecebcb4110f7bcfd0926b1059a64d38"

PR = "r4"

DEPENDS = "nodejs-native"

SRC_URI[md5sum] = "7dfebcdfa93908791f301fddb407e95b"
SRC_URI[sha256sum] = "5f613931cf43a34922bc08403f4cb0006b8c47c04f5502a519320c3b19e5b5bb"

inherit native

# SRC_URI = "https://raw.github.com/gist/840581/4958bc813d03a0fa49f56c1e343883c684150753/yoke.js \
# SRC_URI = "https://gist.github.com/StanAngeloff/840581/raw/9d1b5998a9d528178dec1bdcf3fdd5215c350c08/yoke.js"

# yoke version patched by chitz that prevents duplicate inclusions
SRC_URI = "file://yoke.js \
           file://license"

# hack to replace sprocketize with yoke
do_install() {
    install -d ${D}/${bindir}
    install -m 755 ${WORKDIR}/yoke.js ${D}/${bindir}/yoke
}
