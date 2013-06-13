# help dependency package
DESCRIPTION = "metapackage for dSS11 manuals"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"

PR="r2"

inherit task

RDEPENDS_${PN} = "dss11-installation-manual-de-de \
                  dss11-installation-manual-fr-ch \
                  dss11-installation-manual-nl-nl \
                  dss11-installation-manual-tr-tr \
                  dss11-installation-manual-en-us"
