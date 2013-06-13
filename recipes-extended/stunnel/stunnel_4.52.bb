require stunnel.inc

SRC_URI += "ftp://ftp.nluug.nl/pub/networking/stunnel/stunnel-${PV}.tar.gz \
	   file://init \
	   file://stunnel.conf"

SRC_URI[md5sum] = "f5e713dda0e8efa659f372832ecd0c2c"
SRC_URI[sha256sum] = "7c78c178074e9b96331518a9c309d2e95ca9ad6e0338a96d5ab8ad47fde4347c"
