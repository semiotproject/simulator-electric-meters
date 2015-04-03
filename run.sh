#!/bin/bash

APP_DIR=./simulator
APP_NAME=simulator-electric-meters-1.0-SNAPSHOT-jar-with-dependencies
SIMULATOR_CONFIG=https://raw.githubusercontent.com/semiotproject/simulator-electric-meters/master/dockerfiles/configEnergy.xml
pushd $APP_DIR

git pull

mvn clean package -DskipTests=true

#wget ${SIMULATOR_CONFIG} -O configEnergy.xml

java -jar target/${APP_NAME}.jar configEnergy.xml

popds
