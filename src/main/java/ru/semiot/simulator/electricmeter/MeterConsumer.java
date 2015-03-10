package ru.semiot.simulator.electricmeter;

import ru.semiot.simulator.electricmeter.utils.Generator;
import ru.semiot.simulator.electricmeter.utils.StateData;
import madkit.kernel.Message;
import madkit.message.StringMessage;

/**
 *
 * @author Даниил
 */
public class MeterConsumer extends Meter {

    public MeterConsumer() {
    }

    @Override
    protected void live() {
        while (isAlive()) {
            double amperage, voltage;
            Message m = purgeMailbox();
            if (m == null) {
                m = waitNextMessage();
            }
            if (m.getSender().getRole().equals(EnergyOrganization.METER_ROLE_ORIGIN) && m.getSender().getGroup().equals(getGroup())) {
                amperage = Generator.generateAmperage();
                if (((StringMessage) m).getContent().equals("Give me your amperage")) {
                    amperage = Generator.generateAmperage();
                    sendReplyWithRole(m, new StringMessage(Double.toString(amperage)), getRole());
                } else {
                    printString("I get request. \nI consumed:");
                    double resistance = Generator.generateRisistanceOnWire();
                    voltage = Double.parseDouble(((StringMessage) m).getContent()) - amperage * resistance;
                    data = StateData.createFromValue(amperage, voltage);
                    printString(data.toString());
                    setData();
                    sendReplyWithRole(m, new StringMessage(data.getStringData()), getRole());
                }
            }
        }
    }
}
