#!/bin/bash

# Setup the startup.
$BIN_DIR/setup.sh

# Start scheduler.
su-exec root crond

# Start scheduler job queue.
/usr/sbin/mosquitto -c $ETC_DIR/mosquitto.conf