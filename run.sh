#!/bin/sh
./mvnw clean install && docker-compose up --build
