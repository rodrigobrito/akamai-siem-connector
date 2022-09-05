#!/bin/bash

# Setup the startup.
$BIN_DIR/setup.sh

# Start scheduler.
su-exec root crond

# Start scheduler job queue.
cat $ETC_DIR/banner.txt
echo "[$(date)][$HOSTNAME are now scheduling jobs]"

/usr/sbin/mosquitto -c $ETC_DIR/mosquitto.conf &

tail -f $LOGS_DIR/scheduler.log