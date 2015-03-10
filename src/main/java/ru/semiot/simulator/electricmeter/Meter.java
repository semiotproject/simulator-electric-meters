package ru.semiot.simulator.electricmeter;

import ru.semiot.simulator.electricmeter.coap.TestimonialStore;
import ru.semiot.simulator.electricmeter.utils.LauncherSimulation;
import ru.semiot.simulator.electricmeter.utils.StateData;
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

    private static final ru.semiot.simulator.electricmeter.coap.TestimonialStore store = TestimonialStore.getInstance();
    private String manufacture_date;
    private String type;    
    private String modelType;
    private String serialNumber;
    private String verificationDate;
    private String nextVerificationDate;
    public static int nbOfMeterOnScreenX = 0;
    public static int nbOfMeterOnScreenY = 0;
    protected StateData data;
    private String group;    
    private String role;
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

    public int getSerialNumber() {
        return Integer.parseInt(serialNumber);
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

    @Override
    protected void activate() {
        //pause(LauncherSimulation.getPause());
        stop = false;
        createGroupIfAbsent(EnergyOrganization.COMMUNITY, getGroup(), true, null);
        requestRole(EnergyOrganization.COMMUNITY, getGroup(), getRole(), null);
        data=new StateData();
        data.setData(3, 220);
        setData();
        printString("I am meter: \nmodel is " + getModelType() + " serial is " + getSerialNumber() + " Manufacture date is " + getManufactureDate() + "\n of " + group + " with role " + role + "!");
    }

    protected void setData() {
        store.setData(getSerialNumber(), getData());
    }

    @Override
    protected void end() {
        AbstractAgent.ReturnCode returnCode1;
        returnCode1 = leaveRole(EnergyOrganization.COMMUNITY, getGroup(), getRole());
        if (returnCode1 == AbstractAgent.ReturnCode.SUCCESS ) {
            if (logger != null) {
                logger.info("I am leaving the artificial society");
            }
        } else {
            if (logger != null) {
                logger.warning("something wrong when ending, return code is " + returnCode1 );
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
        System.out.print(str);

    }

    @Override
    public void setupFrame(JFrame frame) {
        int width = 450;
        int height = 300;
        JPanel p = new JPanel(new BorderLayout());
        frame.setTitle(group + " " + role);
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
