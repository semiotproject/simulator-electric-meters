package ru.semiot.simulator.electricmeter;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Agent;
import ru.semiot.simulator.electricmeter.coap.TestimonialStore;
import ru.semiot.simulator.electricmeter.utils.StateData;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public abstract class Meter extends Agent {

    private static final ru.semiot.simulator.electricmeter.coap.TestimonialStore store = TestimonialStore.getInstance();
    private String type;    
    private String modelType;
    private String serialNumber;
    protected StateData data;
    private String group;    
    private String role;
    
    public void setMeter(String _type, String _modelType, String _serialNumber) {
        modelType = _modelType;
        type = _type;
        serialNumber = _serialNumber;
    }

    @Override
    protected void activate() {
        //pause(LauncherSimulation.getPause());
        
        createGroupIfAbsent(EnergyOrganization.COMMUNITY, getGroup(), true, null);
        requestRole(EnergyOrganization.COMMUNITY, getGroup(), getRole(), null);
        data=new StateData();
        data.setData(3, 220);
        setData();
        printString("I am meter: serial is " + getSerialNumber() + " of " + group + " with role " + role + "!\n");
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
        /*if (logger != null) {
            logger.info(str);
        }*/
        System.out.print(str);

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
}
