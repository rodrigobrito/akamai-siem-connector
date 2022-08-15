#!/bin/bash

echo Building base-consumer...
cd base-consumer
./build.sh

echo
echo Building consumer...
cd ../consumer
./build.sh

echo
echo Building base-processor...
cd ../base-processor
./build.sh

echo
echo Building processor-kafka...
cd ../processor-kafka
./build.sh
cd ..

# Read all environment variables.
source ./.env

# Authenticate in the registry repository.
echo $REPOSITORY_PASSWORD | docker login -u $REPOSITORY_ID $REPOSITORY_URL --password-stdin

echo
echo Building docker images...
docker-compose --profile build build
