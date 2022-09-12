#!/bin/bash

# Show the banner.
cat ./banner.txt

# Read all environment variables.
source ./.env

# Authenticate in the docker registry repository.
echo $REPOSITORY_PASSWORD | docker login -u $REPOSITORY_ID $REPOSITORY_URL --password-stdin

# Publish the container images in the docker registry repository.
docker-compose --profile build push