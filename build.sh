#!/bin/bash

cd base-consumer

./build.sh

cd ../consumer

./build.sh

cd ../base-processor

./build.sh

cd ../processor-kafka

./build.sh

cd ..

docker-compose --profile build build