package Meter;

import Utils.StateData;
import java.util.List;
import madkit.kernel.Message;
import madkit.message.StringMessage;

/**
 *
 * @author Даниил
 */
public class MeterMiddle extends Meter {

    public MeterMiddle() {
    }

    @Override
    protected void live() {
        while (isAlive()) {
            Message m = purgeMailbox();
            if (m == null) {
                m = waitNextMessage();
            }
            double receivedVoltage = Double.parseDouble(((StringMessage) m).getContent());
            double trueReceivedVoltage = receivedVoltage;

            List<Message> requestData = null;
            if (isRole(EnergyOrganization.COMMUNITY, getOptionGroup(), EnergyOrganization.METER_ROLE_CONSUMER)) {
                requestData = broadcastMessageWithRoleAndWaitForReplies(
                        EnergyOrganization.COMMUNITY,
                        getOptionGroup(),
                        EnergyOrganization.METER_ROLE_CONSUMER,
                        new StringMessage("Give me your amperage"),
                        EnergyOrganization.METER_ROLE_ORIGIN,
                        900);
            }
            if (noConsumer(requestData)) {
                printString("I don't find consumer!");
                sendReplyWithRole(m, new StringMessage("Haven't consumer"), getRole());
            } else {
                double totalAmperage = 0;
                for (Message i : requestData) {
                    totalAmperage += Double.parseDouble(((StringMessage) i).getContent());
                }
                double resistOnWire = Utils.Generator.generateRisistanceOnWire(totalAmperage);
                trueReceivedVoltage -= resistOnWire * totalAmperage;
                printString("I get voltage: " + trueReceivedVoltage);
                printString("My group consumed:");
                requestData = null;

                requestData = broadcastMessageWithRoleAndWaitForReplies(
                        EnergyOrganization.COMMUNITY,
                        getOptionGroup(),
                        EnergyOrganization.METER_ROLE_CONSUMER,
                        new StringMessage(Double.toString(trueReceivedVoltage)),
                        EnergyOrganization.METER_ROLE_ORIGIN,
                        900);

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
                    sendReplyWithRole(m, new StringMessage("Haven't consumer"), getRole());
                } else {
                    printString(data.toString());
                    setData();
                    sendReplyWithRole(m, new StringMessage(data.getStringData()), getRole());
                }
            }
        }
    }

    private boolean noConsumer(List<Message> reqData) {
        return reqData == null || reqData.isEmpty();
    }
}
