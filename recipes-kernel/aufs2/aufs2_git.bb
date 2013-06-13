DESCRIPTION = "Advanced multi layered unification filesystem version 2."
HOMEPAGE = "http://aufs.sourceforge.net/"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=892f569a555ba9c07a568a7c0c4fa63a"
DEPENDS = "virtual/kernel"
RDEPENDS_${PN} = "update-modules"

SRCREV = "a57d133feea6e0ad3be1e05dc776ce8ad71a0503"
SRC_URI = "git://aufs.git.sourceforge.net/gitroot/aufs/aufs2-standalone.git;protocol=git;branch=aufs2-32"

PV = "git+${SRCREV}"
PR = "r5"

inherit module

S = "${WORKDIR}/git"

TARGET_CC_ARCH += "${LDFLAGS}"
EXTRA_OEMAKE = "KDIR=${STAGING_KERNEL_DIR}"

do_configure_append() {
    if [[ ! -e ${STAGING_KERNEL_DIR}/scripts/unifdef ]] && \
       [[ -f ${STAGING_DIR_NATIVE}${bindir}/unifdef ]]; then
        ln -s ${STAGING_DIR_NATIVE}${bindir}/unifdef ${STAGING_KERNEL_DIR}/scripts/unifdef
    fi
}

do_compile() {
	LDFLAGS=""
    do_make_scripts
	oe_runmake 
}

do_install() {
	install -d ${D}/${base_libdir}/modules/${KERNEL_VERSION}/drivers/extra/	
    install -d ${D}/${includedir}/linux
	install -m 0644 aufs.ko ${D}/${base_libdir}/modules/${KERNEL_VERSION}/drivers/extra/
    install -m 0644 include/linux/aufs_type.h ${D}/${includedir}/linux/
}

FILES_${PN} = "/lib/modules"
