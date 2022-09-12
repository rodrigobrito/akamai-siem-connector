#!/bin/bash

# Show the banner.
cat ./banner.txt

# Start all or a specific service in a standalone execution.
docker-compose --profile run up -d $1