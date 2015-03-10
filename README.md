# simulator-electric-meters
The application has two modes operation. First is GUI mode, for this only run *.jar file. Second mode is console. For work in console, neccecery start program with two args `-nogui` and select path to file with config.
We use xml to manage application with properties:
* `NumberOfMiddleMeter` - sets number of substations;
* `NumberOfConsumerMeter` - sets number of final consumer;
* `StartDelay` - an initial delay of work;
* `WorkDelay` - sets the delay of data generation;
* `AverageAmperageConsumer` - sets the average value of amperage that consumer wants to get;
* `DeviationAmperageConsumer` - sets the deviation value of this amperage;
* `AverageVoltageOrigin` - sets the average value of the voltage from the power station;
* `DeviationVoltageOrigin` - sets the deviation value of this voltage;
* `AverageResistanceOnWire` - sets the average value of the resistance wires. The value does not depend more on any other factors;
* `DeviationResistanceOnWire` - sets the deviation value of this resistance;
* `CoapPort` - initial port to host simulators.

Run app and open `coap://localhost:[$START_PORT..$START_PORT + $METERS_COUNT]/.well-known/core` with [Copper](https://addons.mozilla.org/ru/firefox/addon/copper-270430/) after `StartDelay` ms. 

- `/voltage` - get voltage in Turtle
- `/amperage` - get amperage in Turtle
- `/power` - get power in Turtle
- `/` - get meter description in Turtle

