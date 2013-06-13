require dss11-image.bb

inherit boot-directdisk

COMPATIBLE_MACHINE = "(qemux86|qemux86-64)"

# reserve extra free space for the ext3 filesystem
IMAGE_ROOTFS_EXTRA_SPACE = "150000"

BOOTDD_KERNEL_APPEND = "root=/dev/sda2 rw"
BOOTDD_TIMEOUT = "1"

ROOTFS = "${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}-${MACHINE}.ext3"

do_bootdirectdisk[depends] += "dss11-image:do_rootfs"

bootdirectdisk_prepend () {
   import bb
   fstypes = bb.data.getVar('IMAGE_FSTYPES', d, True)
   if 'ext3' not in fstypes:
       bb.msg.fatal(bb.msg.domain.Build, "ext3 not in IMAGE_FSTYPES")
   rm -f ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.ext3
   rm -f ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.hdddirect
   rm -f ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.vmdk
   rm -f ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.vmdk
   rm -f ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.ext3
   rm -f ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.hdddirect
}

do_generate_vmdk () {
    qemu-img convert -f raw -O vmdk ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.hdddirect ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.vmdk
    cd ${DEPLOY_DIR_IMAGE}
    rm -f ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.vmdk
    ln -s ${IMAGE_NAME}.vmdk ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.vmdk
    rm -f ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.hdddirect
}

do_generate_vmdk[depends] = "qemu-native:do_populate_sysroot" 
do_generate_vmdk[nostamp] = "1"

addtask do_generate_vmdk after do_bootdirectdisk before do_build

export IMAGE_BASENAME = "dss11-${RELEASE_TYPE}-image"
