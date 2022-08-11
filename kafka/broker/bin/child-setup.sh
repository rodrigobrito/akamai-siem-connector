#!/bin/bash

# Setup the startup.
$BIN_DIR/setup.sh

# Create the log file.
if [ ! -f "$LOGS_DIR/kafka-broker.log" ]; then
  touch $LOGS_DIR/kafka-broker.log
fi