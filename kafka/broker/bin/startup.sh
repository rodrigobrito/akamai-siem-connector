#!/bin/bash

# Setup the startup.
$BIN_DIR/child-setup.sh

# Start kafka specifying the configuration file.
/opt/kafka/bin/kafka-server-start.sh $ETC_DIR/server.properties > $LOGS_DIR/kafka-broker.log &

tail -f $LOGS_DIR/kafka-broker.log