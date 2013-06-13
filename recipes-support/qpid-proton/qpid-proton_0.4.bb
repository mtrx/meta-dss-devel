# amqp
DESCRIPTION = "Apache Qpid Proton"
HOMEPAGE = "http://qpid.apache.org/proton/index.html"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=84fed7f8500a5163842545fd59d9e05c"
SRC_URI = "http://sk.freebsd.org/pub/apache/dist/qpid/proton/0.4/qpid-proton-${PV}.tar.gz"
DEPENDS = "openssl"
PR = "r1"

SRC_URI[md5sum] = "73d1a18017010934b2780ccc820f0773"
SRC_URI[sha256sum] = "0b806c10230f54698676f694fff0507a5ff1034674e19024c4252056e6ea6cb5"

inherit cmake

EXTRA_OECMAKE = "-DBUILD_PYTHON=OFF -DBUILD_PHP=OFF -DBUILD_PERL=OFF -DBUILD_RUBY=OFF -DBUILD_JAVA=OFF"
