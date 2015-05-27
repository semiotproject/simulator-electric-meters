package ru.semiot.simulator.electricmeter;

import java.util.List;
import madkit.kernel.AbstractAgent;
import madkit.kernel.Agent;
import ru.semiot.simulator.electricmeter.coap.Server;
import ru.semiot.simulator.electricmeter.utils.StateData;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public abstract class Meter extends Agent {

    private String type;
    private String modelType;
    private String serialNumber;
    protected StateData data;
    private String group;
    private String role;
    private Server server;
    private int port;

    private List<String> nextMeter;
    private String prevMeter;

    public void setMeter(String _type, String _modelType, String _serialNumber) {
        modelType = _modelType;
        type = _type;
        serialNumber = _serialNumber;
    }

    @Override
    protected void activate() {
        createGroupIfAbsent(EnergyOrganization.COMMUNITY, getGroup(), true, null);
        requestRole(EnergyOrganization.COMMUNITY, getGroup(), getRole(), null);
        server = new Server(port);
        server.start();        
    }

    protected void setData() {
        server.update(null, data);
    }

    public void setTopology(String prev, List<String> next) {
        this.prevMeter = prev;
        this.nextMeter = next;
    }

    @Override
    protected void end() {
        AbstractAgent.ReturnCode returnCode1;
        returnCode1 = leaveRole(EnergyOrganization.COMMUNITY, getGroup(), getRole());
        if (returnCode1 == AbstractAgent.ReturnCode.SUCCESS) {
            if (logger != null) {
                logger.info("I am leaving the artificial society");
            }
        } else {
            if (logger != null) {
                logger.warning("something wrong when ending, return code is " + returnCode1);
            }
        }
        if (logger != null) {
            logger.info("I am done");
        }
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
