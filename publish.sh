#!/bin/bash

# Publish the container images in the docker registry repository.
source ./.env

echo $REPOSITORY_PASSWORD | docker login -u $REPOSITORY_ID $REPOSITORY_URL --password-stdin

docker-compose --profile build push