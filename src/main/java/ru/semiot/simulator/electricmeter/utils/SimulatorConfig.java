package ru.semiot.simulator.electricmeter.utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
@Config.LoadPolicy(Config.LoadType.FIRST)
@Config.Sources({
    "classpath:configEnergy.properties"
})
public interface SimulatorConfig extends Config {

    public static SimulatorConfig conf = ConfigFactory.create(
            SimulatorConfig.class);

    
    @DefaultValue("localhost")
    @Key("hostname")
    public String getHostName();

    @DefaultValue("3")
    @Key("NumberOfMiddleMeters")
    public int getNbOfAgentsMiddle();

    @DefaultValue("7")
    @Key("NumberOfConsumerMeter")
    public int getNbOfAgentsConsumer();

    @DefaultValue("3000")
    @Key("StartDelay")
    public int getStartDelay();

    @DefaultValue("5000")
    @Key("WorkDelay")
    public int getWorkDelay();

    @DefaultValue("6")
    @Key("AverageAmperageConsumer")
    public double getAverageAmperageConsumer();

    @DefaultValue("60")
    @Key("DeviationAmperageConsumerPersent")
    public int getDeviationAmperagePersent();

    @DefaultValue("220")
    @Key("AverageVoltageOrigin")
    public double getAverageVoltageOrigin();

    @DefaultValue("5")
    @Key("DeviationVoltageOriginPersent")
    public int getDeviationVoltagePersent();

    @DefaultValue("7.5")
    @Key("AverageResistanceOnWire")
    public double getAverageResistanceOnWire();

    @DefaultValue("33")
    @Key("DeviationResistanceOnWirePersent")
    public int getDeviationResistanceOnWirePersent();

    @DefaultValue("40000")
    @Key("StartPort")
    public int getCoapPort();

    @DefaultValue("coap://localhost:3131/register")
    @Key("registerURI")
    public String getCoapRegister();

}
