#!/bin/sh
# parse firmware image file and print the firmware version to stdout
# to not print anything in case of error, just return 1
# on success return 0

if [[ ! -f "$1" ]]; then
    exit 1
fi

HEADER=`head -c 4 $1 2>/dev/null`
if [[ "$?" -ne 0 ]]; then
    exit 1
fi

if [[ "x$HEADER" != "xaizo" ]]; then
    exit 1
fi

TEMPFILE=`mktemp 2>/dev/null`
if [[ "$?" -ne 0 ]]; then
    exit 1
fi

if [[ -z "$TEMPFILE" ]]; then
    exit 1
fi

dd if=$1 skip=32 bs=1 count=4 of=$TEMPFILE 2>/dev/null
if [[ "$?" -ne 0 ]]; then
    exit 1
fi

VERSION=`od -L $TEMPFILE | awk '{ print $2 }' 2>/dev/null`
if [[ "$?" -ne 0 ]]; then
    #rm -f $TEMPFILE
    exit 1
fi

#rm -f $TEMPFILE

if [[ -z "$VERSION" ]]; then
    exit 1
fi

V1=`echo "$((($VERSION & 4278190080) >> 24))" 2>/dev/null`
V2=`echo "$((($VERSION & 16711680) >> 16))" 2>/dev/null`
V3=`echo "$((($VERSION & 65280) >> 8))" 2>/dev/null`
V4=`echo "$(($VERSION & 255))" 2>/dev/null`

if [[ "x$V1" == "x" ]] && [[ "x$V2" == "x" ]] && [[ "x$V3" == "x" ]] && [[ "x$V4" == "x" ]]; then
    exit 1
fi

echo "$V1.$V2.$V3.$V4"

exit 0
