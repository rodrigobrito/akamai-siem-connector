#!/bin/bash

# Setup the startup.
$BIN_DIR/setup.sh

# Execute the main script using nodejs.
node $BIN_DIR/main.js > $LOGS_DIR/consumer.log &

tail -f $LOGS_DIR/consumer.log