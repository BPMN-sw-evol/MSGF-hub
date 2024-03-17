#!/bin/bash

mvn clean package -DskipTests

docker compose build
