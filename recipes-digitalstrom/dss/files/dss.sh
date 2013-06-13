#!/bin/sh

DESC="digitalSTROM Server"
DAEMON=/usr/bin/dss

OPTIONS="-d"

set_green()
{
    echo 0 > /sys/class/leds/red/brightness
    echo 200 > /sys/class/leds/green/brightness
    echo 0 > /sys/class/leds/blue/brightness
}

set_red()
{
    echo 255 > /sys/class/leds/red/brightness
    echo 0 > /sys/class/leds/green/brightness
    echo 0 > /sys/class/leds/blue/brightness
}

set_blue()
{
    echo 0 > /sys/class/leds/red/brightness
    echo 0 > /sys/class/leds/green/brightness
    echo 255 > /sys/class/leds/blue/brightness
}
case "$1" in
  start)
    echo -n "Starting $DESC: "
    
    set_blue
    if ! start-stop-daemon --oknodo --start --exec $DAEMON -- $OPTIONS ; then
        echo "failed." 
        set_red
        exit 1
    fi
    echo "$NAME."
    set_green
    ;;
  stop)
    echo -n "Stopping $DESC: "
    start-stop-daemon --oknodo --stop --exec $DAEMON
    set_blue
    echo "$NAME."
    ;;
  restart)
    $0 stop
    $0 start
    ;;
  *)
  echo "Usage: $0 {start|stop|restart}" >&2
  exit 1
  ;;
esac

exit 0


