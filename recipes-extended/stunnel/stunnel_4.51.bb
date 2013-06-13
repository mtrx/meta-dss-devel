require stunnel.inc

SRC_URI += "ftp://ftp.nluug.nl/pub/networking/stunnel/stunnel-${PV}.tar.gz \
	   file://init \
	   file://stunnel.conf"

SRC_URI[md5sum] = "af8a770d3b239109ba31d2ccde57be59"
SRC_URI[sha256sum] = "dc52b22de48a2d71ab6170adb628dbe05dd406d6c9103fc43fbdbda776c3e90b"
