# require ${OETREE}/OE/openembedded-core/meta/recipes-core/tasks/task-core-sdk.bb

DESCRIPTION = "Host packages for digitalSTROM Server SDK"
LICENSE = "MIT"
ALLOW_EMPTY = "1"
PR = "r2"

#PACKAGES = "${PN}"
#RDEPENDS_${PN} += "python-native lua5.1-native swig-native"

RDEPENDS_task-core-sdk += "python-native lua5.1-native swig-native"
