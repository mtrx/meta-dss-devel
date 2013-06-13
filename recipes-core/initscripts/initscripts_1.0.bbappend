PRINC = "2"

do_configure_append_at91sam9g20ek() {
    sed -i s#noncpfs#noncpfs,aufs#g ${WORKDIR}/mountall.sh
}

# we will share the feed between our old at91sam9g20ek config and dss11-1gb
PACKAGE_ARCH="${MACHINE_ARCH}"
