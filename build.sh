#!/bin/bash

# Build the microservices.
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

# Build the container images.
echo
echo Building docker images...
docker-compose --profile build build
