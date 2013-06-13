#!/bin/sh

EVENT=$1
DNAME=$2
FNAME=$3
MAIL="$DNAME/$FNAME"
#logger "$0: event $EVENT, dir $DNAME, file $FNAME"

sendme ()
{
   sendmail -t < $1
   RC=$?
   case "$RC" in
     0)
        ;;
     *)
        logger "$0: sending mail $1 failed: $RC"
        ;;
   esac
}

case $EVENT in
   n)
      if [ -f $MAIL ]; then
         if [ "x`fuser $MAIL`" == "x" ]; then
            sendme $MAIL
            rm -f $MAIL
         fi
      fi
      ;;
   *)
      ;;
esac

