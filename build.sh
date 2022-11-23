#!/bin/bash

# Show the banner.
if [ -f "./banner.txt" ]; then
  cat ./banner.txt
fi

# Find NPM and Docker installation in the os path.
NPM_CMD=`which npm`
DOCKER_CMD=`which docker`
DOCKER_COMPOSE_CMD=`which docker-compose`

# Check if NPM is installed.
if [ ! -f "$NPM_CMD" ]; then
  echo "NPM is not installed. Please installed it to continue!"

  exit 1
fi

# Check if the Docker is installed.
if [ ! -f "$DOCKER_CMD" ]; then
  echo "Docker is not installed. Please installed it to continue!"

  exit 1
fi

# Check if the Docker Compose is installed.
if [ ! -f "$DOCKER_COMPOSE_CMD" ]; then
  echo "Docker Compose is not installed. Please installed it to continue!"

  exit 1
fi

# Build the services.
echo Building base-consumer...
cd base-consumer
./build.sh || exit 1

echo
echo Building consumer...
cd ../consumer
./build.sh || exit 1

echo
echo Building base-processor...
cd ../base-processor
./build.sh || exit 1

echo
echo Building processor-kafka...
cd ../processor-kafka
./build.sh || exit 1

echo
echo Building json-converter...
cd ../json-converter
./build.sh || exit 1
cd ..

# Build the container images.
echo
echo Building docker images...

cd ./iac

$DOCKER_COMPOSE_CMD --profile build build

cd ..