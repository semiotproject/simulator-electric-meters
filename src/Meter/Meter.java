package Meter;

import COAP.TestimonialStore;
import Utils.StateData;
import java.util.Calendar;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import madkit.gui.OutputPanel;
import madkit.kernel.AbstractAgent;
import madkit.kernel.Agent;

/**
 *
 * @author Даниил
 */
public abstract class Meter extends Agent {

    private static final COAP.TestimonialStore store = TestimonialStore.getInstance();
    protected String manufacture_date;
    protected String type;
    protected String serverName;
    protected String modelType;
    protected String serialNumber;
    protected String verificationDate;
    protected String nextVerificationDate;
    public static int nbOfMeterOnScreenX = 0;
    public static int nbOfMeterOnScreenY = 0;
    protected StateData data;
    protected String group;
    protected String group2;
    protected String role2;
    protected String role;
    protected JPanel blinkPanel;
    private boolean stop;

    public void setMeter(String _type, String _modelType, String _manufactureDate, String _serialNumber, String _verificationDate, String _nextVerificationDate) {
        setMeter(_type, _modelType, _manufactureDate, _serialNumber);
        verificationDate = _verificationDate;
        nextVerificationDate = _nextVerificationDate;
    }

    public void setMeter(String _type, String _modelType, String _manufactureDate, String _serialNumber) {
        manufacture_date = _manufactureDate;
        modelType = _modelType;
        type = _type;
        serialNumber = _serialNumber;
        verificationDate = Calendar.getInstance().getTime().toString();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        nextVerificationDate = c.getTime().toString();
    }

    public void setVerificationDate(String date) {
        verificationDate = date;
    }

    public void setNextVerificationDate(String date) {
        nextVerificationDate = date;
    }

    public String getVerificationDate() {
        return verificationDate;
    }

    public String getNextVerificationDate() {
        return nextVerificationDate;
    }

    public String getManufactureDate() {
        return manufacture_date;
    }

    public String getType() {
        return type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setServer(String _serverName) {
        serverName = _serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public String getModelType() {
        return modelType;
    }

    public StateData getData() {
        return data;
    }

    public void setRole(String _role) {
        role = _role;
    }

    public String getRole() {
        return role;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

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

    @Override
    protected void activate() {
        stop = false;
        createGroupIfAbsent(EnergyOrganization.COMMUNITY, getGroup(), true, null);
        requestRole(EnergyOrganization.COMMUNITY, getGroup(), getRole(), null);
        if (getOptionGroup() != null && getOptionRole() != null) {
            createGroupIfAbsent(EnergyOrganization.COMMUNITY, getOptionGroup(), true, null);
            requestRole(EnergyOrganization.COMMUNITY, getOptionGroup(), getOptionRole(), null);
        }
        if (logger != null) {
            logger.info("I am meter: \nmodel is " + getModelType() + " serial is " + getSerialNumber() + " Manufacture date is " + getManufactureDate() + "\n of " + group + " with role " + role + "!");
        }
    }

    protected void setData() {
        store.setData(getSerialNumber(), getData().toString());
    }

    @Override
    protected void end() {
        AbstractAgent.ReturnCode returnCode1;
        AbstractAgent.ReturnCode returnCode2 = null;
        if (getOptionGroup() != null) {
            returnCode2 = leaveRole(EnergyOrganization.COMMUNITY, getOptionGroup(), getOptionRole());
        }
        returnCode1 = leaveRole(EnergyOrganization.COMMUNITY, getGroup(), getRole());
        if (returnCode1 == AbstractAgent.ReturnCode.SUCCESS && (returnCode2 == null || returnCode2 == AbstractAgent.ReturnCode.SUCCESS)) {
            if (logger != null) {
                logger.info("I am leaving the artificial society");
            }
        } else {
            if (logger != null) {
                logger.warning("something wrong when ending, return code is " + returnCode1 + (returnCode2 == null ? (" Code2 is " + returnCode2) : ""));
            }
        }
        if (logger != null) {
            logger.info("I am done");
        }
    }

    void printString(String str) {
        if (logger != null) {
            logger.info(str);
        }

    }

    @Override
    public void setupFrame(JFrame frame) {
        int width = 450;
        int height = 300;
        JPanel p = new JPanel(new BorderLayout());
        if (role2 == null) {
            frame.setTitle(group + " " + role);
        } else {
            frame.setTitle(group2 + " " + role2);
        }

        //customizing but still using the OutputPanel from MaDKit GUI
        p.add(new OutputPanel(this), BorderLayout.CENTER);
        blinkPanel = new JPanel();
        p.add(blinkPanel, BorderLayout.NORTH);
        p.validate();
        frame.add(p);
        int xLocation = nbOfMeterOnScreenX++ * width;
        int yLocation = nbOfMeterOnScreenY * height;
        if (xLocation + width > Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
            nbOfMeterOnScreenX = 0;
            xLocation = 0;
            nbOfMeterOnScreenY++;
        }
        if (yLocation + height > Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {
            nbOfMeterOnScreenY = 0;
            yLocation = 0;
        }

        frame.setLocation(xLocation, yLocation);
        frame.setSize(width, height);
    }

    @Override
    public boolean isAlive() {
        return !stop;
    }

    public void stop() {
        stop = true;
    }
}
