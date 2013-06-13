PRINC="1"
PACKAGES =+ "${PN}-so-links"
FILES_${PN}-so-links = "${libdir}/libssl.so ${libdir}/libcrypto.so"
RDEPENDS_${PN}-dev = "${PN}-so-links"
RREPLACES_${PN} = "libcrypto0.9.8"
RCONFLICTS_${PN} = "libcrypto0.9.8"
# We really need the symlink so :P
ERROR_QA = "debug-deps dev-deps debug-files arch la2 pkgconfig la perms"

