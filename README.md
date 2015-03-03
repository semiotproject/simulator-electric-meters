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
* `CoapTTL`- Time To Live in ms for CoAP packages;
* `CoapPort`.
Run app and open `coap://localhost:5683/.well-known/core` with Copper after `StartDelay` seconds. The public CoAP interface is:
    `GET` or `OBSERVE` `/subscribe` to find all available meters, their addresses and current heat rate
    `GET` or `OBSERVE` `/subscribe/[0..$METERS-COUNT]` to find a particular meter

