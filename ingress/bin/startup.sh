#!/bin/bash

# Setup the startup.
$BIN_DIR/setup.sh

# Start nginx in foreground mode.
nginx -g daemon off