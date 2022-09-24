#!/bin/bash

# Show the banner.
if [ -f "./banner.txt" ]; then
  cat ./banner.txt
fi

# Find Docker installation in the os path.
DOCKER_CMD=`which docker`
DOCKER_COMPOSE_CMD=`which docker-compose`

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

# Read all environment variables.
source ./.env

# Authenticate in the docker registry repository.
echo $REPOSITORY_PASSWORD | $DOCKER_CMD login -u $REPOSITORY_ID $REPOSITORY_URL --password-stdin

# Publish the container images in the docker registry repository.
$DOCKER_COMPOSE_CMD --profile build push