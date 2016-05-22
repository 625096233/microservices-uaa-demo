#!/usr/bin/env bash

docker-compose -f jhipster-registry.yml up -d
docker-compose -f elk.yml up -d

# cd /Users/russomi/Projects/russomi/microservices-demo/app1 && ./mvnw package -Pprod docker:build && cd ..
# ./mvnw package -Pprod docker:build in /Users/russomi/Projects/russomi/microservices-demo/app2
# ./mvnw package -Pprod docker:build in /Users/russomi/Projects/russomi/microservices-demo/app3
# ./mvnw package -Pprod docker:build in /Users/russomi/Projects/russomi/microservices-demo/gateway
# ./mvnw package -Pprod docker:build in /Users/russomi/Projects/russomi/microservices-demo/uaa
