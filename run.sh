#!/bin/bash

APP_DIR=./target
APP_NAME=simulator-electric-meter-1.0-SNAPSHOT

pushd $APP_DIR

git pull

mvn clean package -DskipTests=true

wget ${SIMULATOR_CONFIG} -O ./configEnergy.xml

java -jar ${APP_DIR}/${APP_NAME}.jar -nogui configEnergy.xml

popd
	


