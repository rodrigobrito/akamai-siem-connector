#!/bin/bash

# Setup the startup.
$BIN_DIR/setup.sh

# Create the log file.
if [ ! -f "$LOGS_DIR/kafka-broker.log" ]; then
  touch $LOGS_DIR/kafka-broker.log
fi

# Check if zookeeper booted before start the broker.
now=$(date)
message="[$now][Waiting for $ZOOKEEPER_SERVICE_HOST to start on port $ZOOKEEPER_SERVICE_PORT"

echo $message
echo $message >> $LOGS_DIR/kafka-broker.log

while [ true ]
do
  status=$(echo stat | nc $ZOOKEEPER_SERVICE_HOST $ZOOKEEPER_SERVICE_PORT 2>/dev/null)

  if [ ! -z "$status" ]; then
    break
  fi

  sleep 1
done