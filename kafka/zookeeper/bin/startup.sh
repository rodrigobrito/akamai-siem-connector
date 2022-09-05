#!/bin/bash

# Setup the startup.
$BIN_DIR/child-setup.sh

# Start zookeeper specifying the configuration file.
cat $ETC_DIR/banner.txt
/opt/kafka/bin/zookeeper-server-start.sh $ETC_DIR/zookeeper.properties > $LOGS_DIR/zookeeper.log &

tail -f $LOGS_DIR/zookeeper.log