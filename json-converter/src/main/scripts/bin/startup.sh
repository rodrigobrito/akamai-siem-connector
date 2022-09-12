#!/bin/bash

# Setup the startup.
$BIN_DIR/setup.sh

# Execute the main script using nodejs.
cat $ETC_DIR/banner.txt
${BIN_DIR}/run.sh