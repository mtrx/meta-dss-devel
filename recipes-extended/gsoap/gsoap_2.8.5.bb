include gsoap.inc

PR = "r1.${INC_PR}"

DEPENDS = "gsoap-native openssl"

EXTRA_OEMAKE = "SOAP=${STAGING_BINDIR_NATIVE}/soapcpp2"

SRC_URI[md5sum] = "541dcff86d1c6171cb8540cf51a4cfa8"
SRC_URI[sha256sum] = "838b8adefdaa40afcacc6811a73d7bcc19ecf352728647afe72e4203cb512141"

