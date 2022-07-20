#!/bin/bash

$BIN_DIR/setup.sh

/opt/kafka/bin/zookeeper-server-start.sh $ETC_DIR/zookeeper.properties