#!/bin/bash

# Setup the startup.
$BIN_DIR/setup.sh

# Create the log file.
if [ ! -f "$LOGS_DIR/zookeeper.log" ]; then
  touch $LOGS_DIR/zookeeper.log
fi