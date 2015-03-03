package Meter;

import Utils.Generator;
import Utils.StateData;
import static Utils.Config.conf;
import java.util.List;
import madkit.kernel.Message;
import madkit.message.StringMessage;

/**
 *
 * @author Даниил
 */
public class MeterOrigin extends Meter {

    protected boolean firstPause = true;

    public MeterOrigin() {
    }

    @Override
    protected void live() {
        while (isAlive()) {
            if (firstPause) {
                pause(conf.getStartDelay());
                firstPause = false;
            } else {
                pause(conf.getWorkDelay());
            }
            double voltage = Generator.generateVoltage();

            printString("I get some voltage: " + voltage + " and send it");
            List<Message> requestData = null;
            if (isRole(EnergyOrganization.COMMUNITY, EnergyOrganization.METER_GROUP_ORIGIN, EnergyOrganization.METER_ROLE_CONSUMER)) {
                requestData = broadcastMessageWithRoleAndWaitForReplies(
                        EnergyOrganization.COMMUNITY,
                        getGroup(),
                        EnergyOrganization.METER_ROLE_CONSUMER,
                        new StringMessage(Double.toString(voltage)),
                        getRole(),
                        1000);
            }

            if (requestData == null) {
                printString("I don't find consumer!");
            } else if (!requestData.isEmpty()) {
                boolean createData = true;
                for (Message i : requestData) {
                    if (!((StringMessage) i).getContent().equals("Haven't consumer")) {
                        if (!createData) {
                            data = data.addData(((StringMessage) i).getContent());
                        } else {
                            data = StateData.createFromString(((StringMessage) i).getContent());
                            createData = false;
                        }
                    }
                }
                if (createData) {
                    printString("I don't find consumer!");
                } else {
                    printString("Total consumed:");
                    printString(data.toString());
                    setData();
                    printString("All data consumered!");
                }
            } else {
                printString("I don't find consumer!");
            }
        }
    }
}
