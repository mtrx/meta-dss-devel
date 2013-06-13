#!/bin/sh

NAME="runsvdir"
DESC="runit service supervisor"
DAEMON=/usr/sbin/runsvdir

case "$1" in
  start)
    if [ ! -d /etc/runit ]; then
        mkdir -p /etc/runit
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
