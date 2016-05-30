#!/usr/bin/env bash

docker-compose -f docker/jhipster-registry.yml up -d
docker-compose -f elk.yml up -d
