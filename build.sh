#!/bin/bash

cd consumer

./build.sh

cd ..

docker-compose --profile build build