# simulator-electric-meters

## How to launch the application using [Docker](https://www.docker.com/):

* Pull the image:
```bash
sudo docker pull semiot/simulator-heat-meters
```
* Run the container:
```bash
sudo docker run \
-i -t \
-v /semiot-platform/simulator-electric-meters:/semiot-platform/simulator-electric-meters \
--expose=40000-41000/udp \
semiot/simulator-electric-meters
```

## How to check simulators are working:

Open in [Copper](https://addons.mozilla.org/ru/firefox/addon/copper-270430/) next URI: `coap://${HOSTNAME}:${[startPort..startPort + count]}/.well-known/core` and "observe" registrations on `coap://${HOSTNAME}:${[startPort..startPort + count]}/amperage`. 

## How to manage simulators' configuration:

Create Java properties file `/semiot-platform/simulator-electric-meters/config.properties` with next parameters:

  * `hostname` - ip or name of simulators' base host, using in meter description;
  * `NumberOfMiddleMeters` - number of substations;
  * `StartDelay` - an initial delay of work, ms;
  * `WorkDelay` - sets the delay of data generation, ms;
  * `AverageAmperageConsumer` - sets the average value of amperage that consumer wants to get;
  * `DeviationAmperageConsumer` - sets the deviation value of this amperage;
  * `AverageVoltageOrigin` - sets the average value of the voltage from the power station;
  * `DeviationVoltageOrigin` - sets the deviation value of this voltage;
  * `AverageResistanceOnWire` - sets the average value of the resistance wires. The value does not depend more on any other factors;
  * `DeviationResistanceOnWire` - sets the deviation value of this resistance;
  * `StartPort` - initial port for simulators; ports `[${startPort}..${startPort + count}]` would be occupied
  * `registerURI` - URI of available Device Proxy Service

