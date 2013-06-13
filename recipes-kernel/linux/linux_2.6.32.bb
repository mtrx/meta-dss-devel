require linux.inc

PR="r20"

DESCRIPTION = "Linux kernel for dSS11/2010 platform"
KERNEL_IMAGETYPE ?= "uImage"
ARM_KEEP_OAB="0"
COMPATIBLE_MACHINE = "at91sam9g20ek"
DEFAULT_PREFERENCE = "1"
UDEV_GE_141 = "1"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-${PV}.tar.bz2;name=kernel \
           ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/patch-${PV}.8.bz2;apply=yes \
           file://defconfig \
           file://linux26-dss11-8.patch \
           file://linux-2.6.32-002-sam9g20-proper-reset.patch \
           file://rtc-rs5c372-osc.patch \
           file://aufs2-base-32.patch \
           file://aufs2-standalone-32.patch \
           "                     

SRC_URI[kernel.md5sum] = "260551284ac224c3a43c4adac7df4879"
SRC_URI[kernel.sha256sum] = "5099786d80b8407d98a619df00209c2353517f22d804fdd9533b362adcb4504e"


SRC_URI[md5sum] = "eabf01da4c72f7ea5b4e4bf8e8535e5f"
SRC_URI[sha256sum] = "50c08a7ffcad1e7cd2e7c2c906795896dd36ce71249d816914c306dcc5875fd2"

# Perf in 2.6.32 has broken perl handling, so disable it
do_compile_perf() {
    :
}

do_install_perf() {
    :
}

do_install_prepend() {
    mkdir headerstash -p
    cp include/linux/bounds.h headerstash/
    cp include/asm-arm/asm-offsets.h headerstash/
}

do_install_append() {
    cp headerstash/bounds.h $kerneldir/include/linux/
    cp headerstash/asm-offsets.h $kerneldir/include/asm-arm/asm-offsets.h
    rm -rf headerstash/

    install -d ${D}/boot
}

