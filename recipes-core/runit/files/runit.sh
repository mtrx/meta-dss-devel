#!/bin/sh

NAME="runsvdir"
DESC="runit service supervisor"
DAEMON=/usr/sbin/runsvdir

case "$1" in
  start)
    if [ ! -d /etc/runit ]; then
        mkdir -p /etc/runit
    fi
    echo "Stopping running services..."
    for i in $(ls /etc/runit)
    do
        if [[ -f "/etc/runit/$i/supervise/stat" ]]; then
            if [[ "`cat /etc/runit/$i/supervise/stat`" == "run" ]]; then
                echo "Stopping $i..."
                sv down $i
            fi
        fi
    done
    RUNSV_PROCS=$(pidof runsv)
    if [ $? -eq 0 ]; then
        kill $RUNSV_PROCS
    fi
    echo -n "Starting $DESC: "
    if ! start-stop-daemon --oknodo --start --background --exec $DAEMON -- /etc/runit 'log: ...........................................................................................................................................................................................................................................................................................................................................................................................................' ; then
        echo "failed."
        exit 1
    fi
    echo "$NAME."
    ;;
  stop)
    echo -n "Stopping $DESC: "
    start-stop-daemon --oknodo --stop --exec $DAEMON
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
