#!/bin/bash

cd gameit-gateway
mvn package docker:build

cd ..
docker-compose up -d

