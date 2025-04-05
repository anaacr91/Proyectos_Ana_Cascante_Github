#!/bin/bash
./mvnw verify -DskipTests
docker build -t testing_g2_movies_24:1.0.0 .
docker compose down
docker compose up -d
docker logs -f spring_app
# ./docker.sh