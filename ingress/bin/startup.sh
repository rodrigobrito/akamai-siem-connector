#!/bin/bash

# Setup the startup.
$BIN_DIR/setup.sh

# Start nginx in foreground mode.
cat $ETC_DIR/banner.txt
echo "[$(date)][ingress started]"
nginx -g "daemon off;"