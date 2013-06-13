# UPnP library
DESCRIPTION = "Universal Plug and Play (UPnP) SDK"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b3190d5244e08e78e4c8ee78544f4863"
HOMEPAGE = "http://pupnp.sourceforge.net/"

SRC_URI="${SOURCEFORGE_MIRROR}/pupnp/libupnp-${PV}.tar.bz2"

PR="r1"

inherit autotools

EXTRA_OECONF = " --disable-samples --disable-ipv6 "

SRC_URI[md5sum] = "71476b1781ad179bfc9bead640be5f54"
SRC_URI[sha256sum] = "5c1dd7f4c13ab321685063e25b874e44bf31d6e170fe51ddae47abe4f41523ee"


