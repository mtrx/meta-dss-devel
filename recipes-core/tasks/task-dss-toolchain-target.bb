DESCRIPTION = "Packages for digitalSTROM Server SDK"
LICENSE = "MIT"
ALLOW_EMPTY = "1"
PR = "r2"

PACKAGES = "${PN}"

RDEPENDS_${PN} = "\
    dss \
    dss-dev \
    poco-dev \
    boost-dev \
    gsoap-dev \
    libjson-dev \
    libconfig-dev \
    sqlite3 \
    libsqlite3-dev \
    task-sdk-bare \
"
