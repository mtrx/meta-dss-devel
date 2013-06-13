#!/bin/sh

# activate kernel serial line settings
# Workaround for kernel limitation: Emlix does not properly configure 
# the dSS11 RS485 port

case "$1" in
    start)
        echo "Configuring RS485 port..."
        stty -a < /dev/ttyS2
esac

exit 0

