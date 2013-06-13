# Surely Only A Prank
DESCRIPTION = "The gSOAP toolkit provides a unique SOAP-to-C/C++ language \
binding for the development of SOAP Web Services and clients."
SECTION = "devel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b338b08b1b61e028e0f399a4de25e58f"
INC_PR = "1"

SRC_URI = "${SOURCEFORGE_MIRROR}/project/gsoap2/gSOAP/gsoap_${PV}.zip"

inherit autotools

S = "${WORKDIR}/gsoap-2.8"

PARALLEL_MAKE = ""

# do not enable this, otherwise gsoap will decide to compile WITHOUT openssl...
# EXTRA_OECONF = "--enable-ssl"
