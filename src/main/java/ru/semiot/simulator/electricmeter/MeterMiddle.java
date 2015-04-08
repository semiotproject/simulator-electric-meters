package ru.semiot.simulator.electricmeter;

import java.util.List;
import javax.swing.JFrame;
import madkit.kernel.Message;
import madkit.message.StringMessage;
import ru.semiot.simulator.electricmeter.utils.StateData;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class MeterMiddle extends Meter {

    private String group2;
    private String role2;
    
    public void setOptionGroup(String group) {
        group2 = group;
    }

    public void setOptionRole(String role) {
        role2 = role;
    }

    public String getOptionGroup() {
        return group2;
    }

    public String getOptionRole() {
        return role2;
    }
    public MeterMiddle() {
    }
    
    public void createOption(){
        if (getOptionGroup() != null && getOptionRole() != null) {
            createGroupIfAbsent(EnergyOrganization.COMMUNITY, getOptionGroup(), true, null);
            requestRole(EnergyOrganization.COMMUNITY, getOptionGroup(), getOptionRole(), null);
        }
    }
    @Override
    protected void activate(){
        super.activate();
        createOption();
    }
    
    @Override
    public void setupFrame(JFrame frame) {
        super.setupFrame(frame);
        frame.setTitle(getOptionGroup() + " " + getOptionRole());
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
                        5000);
            }
            if (noConsumer(requestData)) {
                //printString("I don't find consumer!");
                sendReplyWithRole(m, new StringMessage("Haven't consumer"), getRole());
            } else {
                double totalAmperage = 0;
                for (Message i : requestData) {
                    totalAmperage += Double.parseDouble(((StringMessage) i).getContent());
                }
                double resistOnWire = ru.semiot.simulator.electricmeter.utils.Generator.generateRisistanceOnWire(totalAmperage);
                trueReceivedVoltage -= resistOnWire * totalAmperage;
                //printString("I get voltage: " + trueReceivedVoltage);
                //printString("My group consumed:");
                requestData = null;

                requestData = broadcastMessageWithRoleAndWaitForReplies(
                        EnergyOrganization.COMMUNITY,
                        getOptionGroup(),
                        EnergyOrganization.METER_ROLE_CONSUMER,
                        new StringMessage(Double.toString(trueReceivedVoltage)),
                        EnergyOrganization.METER_ROLE_ORIGIN,
                        5000);

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
                    //printString("I don't find consumer!");
                    sendReplyWithRole(m, new StringMessage("Haven't consumer"), getRole());
                } else {
                    //printString(data.toString());
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
