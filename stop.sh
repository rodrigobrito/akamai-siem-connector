#!/bin/bash

# Show the banner.
cat ./banner.txt

# Stop all services in a standalone execution.
docker-compose down --remove-orphans

# Clean all volumes.
echo Y | docker volume prune