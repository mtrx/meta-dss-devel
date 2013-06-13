PRINC = "1"

do_configure () {
  sed -i -e 's:/usr/lib:${STAGING_LIBDIR}:g' wscript
  sed -i -e 's:/usr/local/lib:${STAGING_LIBDIR}:g' wscript
  ./configure --prefix=${prefix} --without-snapshot --destdir=${D}
}

