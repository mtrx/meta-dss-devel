# google performance analysis tools
DESCRIPTION = "Fast, multi-threaded malloc() and nifty performance analysis tools"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=762732742c73dc6c7fbe8632f06c059a"
PR = "r1"
ARM_INSTRUCTION_SET = "arm"

SRC_URI = "http://gperftools.googlecode.com/files/gperftools-${PV}.tar.gz"
SRC_URI += "file://makefix.patch"

inherit autotools

do_unpack_append() {
    bb.build.exec_func('do_fix_mode', d)
}

do_fix_mode() {
    chmod u+rw ${S}/*
}

SRC_URI[md5sum] = "13f6e8961bc6a26749783137995786b6"
SRC_URI[sha256sum] = "7de3dd91f018825b1e7d332af1edace15c6211f430186febede1835069861080"
