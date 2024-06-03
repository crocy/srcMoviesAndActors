#!/bin/bash
# subshell to actors dir and build jar
(
  echo "Building Actors JAR"
  cd actors || exit 1
  ./gradlew bootJar
)

# subshell to movies dir and build jar
(
  echo "Building Movies JAR"
  cd movies || exit 1
  ./gradlew bootJar
)

# build docker images
docker-compose build || docker compose build

# start docker images
docker-compose up || docker compose up