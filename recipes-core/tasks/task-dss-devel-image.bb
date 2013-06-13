DESCRIPTION = "dSS11 development image dependency metapackage"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"

PR = "r38"

inherit task

DEPENDS = "task-devel-tools task-lua-extensions"

RDEPENDS_${PN} = "\
    task-system-addons \
    modutils-initscripts \
    bootsetup \
    dropbear \
    lighttpd \
    lighttpd-module-cgi \
    mtd-utils \
    avahi-daemon \
    avahi-autoipd \
    upnpbeacon \
    haserl \
    e2fsprogs-mke2fs \
    e2fsprogs-e2fsck \
    dss11-websetup \
    busybox-ifplugd \
    runit \
    logrotate \
    tzdata \
    tzdata-europe \
    tzdata-atlantic \
    tzdata-africa \
    tzdata-americas \
    tzdata-antarctica \
    tzdata-antarctica \
    tzdata-asia \
    tzdata-australia \
    tzdata-pacific \
    dss-add-on-feeds \
    dss11-utils \
    dss11-help \
    dsm-firmware \
    dsm-firmware-upgrade \
    dsm-firmware-upgrade-locale-en-us \
    dsm-firmware-upgrade-locale-de-de \
    dsa \
    dsa-locale-de-de \
    dsa-locale-en-us \
    dsa-ui-module \
    dss \
    dss-web-module \
    eglibc-binary-localedata-en-us \
    eglibc-binary-localedata-de-de \
    ntp \
    "
#RDEPENDS_${PN}_append = "\
#    kernel-module-scsi-wait-scan \
#    kernel-module-loop \
#    kernel-module-rtlwifi \
#    kernel-module-rtl8192cu \
#    kernel-module-rtl8192c-common \
#    kernel-module-rt2x00lib \
#    kernel-module-rt2800usb \
#    kernel-module-rt2800lib \
#    kernel-module-rt2x00usb \
#    kernel-module-ath \
#    kernel-module-zd1211rw \
#    kernel-module-ecb \
#    kernel-module-hmac \
#    kernel-module-arc4 \
#    kernel-module-aes-generic \
#    kernel-module-ansi-cprng \
#    kernel-module-sha256-generic \
#    kernel-module-crc-itu-t \
#    kernel-module-crc-ccitt \
#    kernel-module-mac80211 \
#    kernel-module-cfg80211 \
#    iw \
#    wireless-tools \
#    wpa-supplicant \
#    wpa-supplicant-passphrase \
#    "

# our qemux86 configuration does not have those modules
#RDEPENDS_${PN}_append_arm = " \
#    kernel-module-ext3 \
#    kernel-module-ext4 \
#    kernel-module-vfat \
#    kernel-module-ntfs \
#"

PACKAGE_ARCH = "${MACHINE_ARCH}"

