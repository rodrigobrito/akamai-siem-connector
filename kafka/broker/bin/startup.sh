#!/bin/bash

$BIN_DIR/setup.sh

/opt/kafka/bin/kafka-server-start.sh $ETC_DIR/server.properties