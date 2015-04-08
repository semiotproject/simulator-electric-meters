# simulator-electric-meters

We use xml to manage application with properties:
* `NumberOfMiddleMeter` - sets number of substations;
* `NumberOfConsumerMeter` - sets number of final consumer;
* `StartDelay` - an initial delay of work, ms;
* `WorkDelay` - sets the delay of data generation, ms;
* `AverageAmperageConsumer` - sets the average value of amperage that consumer wants to get;
* `DeviationAmperageConsumer` - sets the deviation value of this amperage;
* `AverageVoltageOrigin` - sets the average value of the voltage from the power station;
* `DeviationVoltageOrigin` - sets the deviation value of this voltage;
* `AverageResistanceOnWire` - sets the average value of the resistance wires. The value does not depend more on any other factors;
* `DeviationResistanceOnWire` - sets the deviation value of this resistance;
* `StartPort` - initial port to host simulators.
* `registerURI` - the address for registration of meters
* `hostname`
Run app and open `coap://[$HOSTNAME]:[$START_PORT..$START_PORT + $METERS_COUNT]/.well-known/core` with [Copper](https://addons.mozilla.org/ru/firefox/addon/copper-270430/) after `StartDelay` ms. 