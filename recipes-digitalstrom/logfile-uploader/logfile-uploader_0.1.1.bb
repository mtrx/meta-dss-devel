# logfile uploader scripts
DESCRIPTION = "Client-side scripts to upload log files and additional information to a server"
SECTION = "utils"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3;md5=ab735e923e58a6e33297f8a617c45937"
HOMEPAGE = "http://developer.digitalstrom.org"
PACKAGE_ARCH_${PN} = "all"

RDEPENDS_${PN} = "rsync"

PR = "r1"

SRC_URI = "http://developer.digitalstrom.org/download/utilities/${PN}-client-${PV}.tgz"

SRC_URI[md5sum] = "a6123c163149b8550f07ec7a0884d9c2"
SRC_URI[sha256sum] = "5c05db1056adaa64ef0388b75fd9757e81d6c1d8fa73c44a6ff5e18cb8f32bde"

S = "${WORKDIR}/logfile-uploader-client-${PV}"

FILES_${PN} += "/home/root/.ssh/*"

inherit autotools

do_compile () {
    :
}

pkg_postinst() {
    #!/bin/sh
    if [ "x$D" != "x" ]; then
        exit 0
    fi

    if grep upload_logfiles.sh ${sysconfdir}/crontab > /dev/null 2>&1 ; then
        echo "  removing previous upload_logfiles.sh crontab entry" >&2
        grep -v upload_logfiles.sh ${sysconfdir}/crontab > ${sysconfdir}/crontab.no-${PF}
        mv ${sysconfdir}/crontab.no-${PF} ${sysconfdir}/crontab
    fi
    if ! add_crontab_entry.sh ; then
        echo "ERROR $? adding entry to crontab" >&2
    fi
}

pkg_preinst() {
    #!/bin/sh
    if [ "x$D" != "x" ]; then
        exit 0
    fi
    
    killall upload_logfiles.sh 2>/dev/null
    exit 0
}

pkg_prerm() {
    #!/bin/sh
    killall upload_logfiles.sh 2>/dev/null
    echo "  removing upload_logfiles.sh crontab entry" >&2
    grep -v upload_logfiles.sh ${sysconfdir}/crontab > ${sysconfdir}/crontab.no-${PF}
    mv ${sysconfdir}/crontab.no-${PF} ${sysconfdir}/crontab
    echo "  removing registration keys" >&2
    mac=$(get_mac_address.sh)
    if [[ "$mac" ]] ; then
        echo "  removing upload keys" >&2
        rm /home/root/.ssh/${mac}.key       2>/dev/null
        rm /home/root/.ssh/${mac}.key.pub   2>/dev/null
    fi
    rm /home/root/.ssh/dsapps_registration_key 2>/dev/null
    exit 0
}
