package ru.semiot.simulator.electricmeter.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import madkit.kernel.Agent;
import madkit.kernel.Madkit;
import ru.semiot.simulator.electricmeter.EnergyOrganization;
import ru.semiot.simulator.electricmeter.Meter;
import ru.semiot.simulator.electricmeter.MeterConsumer;
import ru.semiot.simulator.electricmeter.MeterMiddle;
import ru.semiot.simulator.electricmeter.MeterOrigin;
import static ru.semiot.simulator.electricmeter.utils.Generator.generateSerial;
import static ru.semiot.simulator.electricmeter.utils.SimulatorConfig.conf;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class LauncherSimulation extends Agent {

    public static int port = conf.getCoapPort();
    private static final ArrayList<Meter> agentsList = new ArrayList<>();
	private static String pathToTopology = null;

    public static void main(String args[]) {
        new Madkit(Madkit.Option.launchAgents.toString(), LauncherSimulation.class.getName() + "," + false + ",1");
        if (args.length >= 1 && !args[0].isEmpty()) {
            pathToTopology = args[0];
        }
    }

    @Override
    protected void activate() {
        Meter meter = new MeterOrigin();
        meter.setMeter("mercury30", "mercury30", generateSerial());
        meter.setGroup(EnergyOrganization.METER_GROUP_ORIGIN);
        meter.setRole(EnergyOrganization.METER_ROLE_ORIGIN);
        meter.setPort(port++);

        agentsList.add(meter);
        for (int i = 0; i < conf.getNbOfAgentsMiddle(); i++) {
            meter = new MeterMiddle();
            meter.setMeter("mercury30", "mercury30", generateSerial());
            meter.setGroup(EnergyOrganization.METER_GROUP_ORIGIN);
            meter.setRole(EnergyOrganization.METER_ROLE_CONSUMER);
            ((MeterMiddle) meter).setOptionGroup(EnergyOrganization.METER_GROUP_OPTION + i);
            ((MeterMiddle) meter).setOptionRole(EnergyOrganization.METER_ROLE_ORIGIN);
            meter.setPort(port++);
            agentsList.add(meter);
        }

        for (int i = 0; i < conf.getNbOfAgentsConsumer(); i++) {
            meter = new MeterConsumer();
            meter.setMeter("mercury30", "mercury30", generateSerial());
            meter.setGroup(EnergyOrganization.METER_GROUP_OPTION + (int) (conf.getNbOfAgentsMiddle() * Math.random()));
            meter.setRole(EnergyOrganization.METER_ROLE_CONSUMER);
            meter.setPort(port++);
            agentsList.add(meter);
        }
        setTopology();
        agentsList.stream().forEach((i) -> {
            launchAgent(i, false);
        });
		if(pathToTopology!=null)
			getTopology(pathToTopology);
    }

    @Override
    protected void live() {
        System.out.println("All meters started");
    }

    private void setTopology() {
        for (Meter m : agentsList) {
            if (m instanceof MeterMiddle) {
                String k = null;
                List <String> l = new ArrayList<>();
                for (Meter i : agentsList) {                    
                    if(i instanceof MeterOrigin && m.getGroup().equals(i.getGroup()) && i.getRole().equals(EnergyOrganization.METER_ROLE_ORIGIN)){
                        k = ("coap://${HOST}:${PORT}/meter".replace("${HOST}", conf.getHostName()).replace("${PORT}", Integer.toString(i.getPort())));                        
                    }
                    if(i instanceof MeterConsumer && ((MeterMiddle)m).getOptionGroup().equals(i.getGroup())){
                        l.add("coap://${HOST}:${PORT}/meter".replace("${HOST}", conf.getHostName()).replace("${PORT}", Integer.toString(i.getPort())));
                    }                        
                }
                m.setTopology(k, !l.isEmpty()?l:null);
            }
            if (m instanceof MeterConsumer) {
                String k=null;
                for (Meter i : agentsList) {                    
                    if((i instanceof MeterMiddle) && m.getGroup().equals(((MeterMiddle)i).getOptionGroup())){
                        k = ("coap://${HOST}:${PORT}/meter".replace("${HOST}", conf.getHostName()).replace("${PORT}", Integer.toString(i.getPort())));
                        break;
                    }
                }
                m.setTopology(k, null);
            }
            if (m instanceof MeterOrigin) {
                List<String> l = new ArrayList<>();
                for (Meter i : agentsList) {                    
                    if(m.getGroup().equals(i.getGroup()) && i.getRole().equals(EnergyOrganization.METER_ROLE_CONSUMER)){
                        l.add("coap://${HOST}:${PORT}/meter".replace("${HOST}", conf.getHostName()).replace("${PORT}", Integer.toString(i.getPort())));
                    }
                }
                m.setTopology(null, !l.isEmpty()?l:null);
            }
        }
    }

    public static boolean getTopology(String filename) {
        String topology = "graph Topology{";
        String origin = "\"Origin\"";
        int k = 0;
        for (Meter i : agentsList) {
            if (i instanceof MeterMiddle) {
                topology += "\n" + origin + "--" + "\"" + ((MeterMiddle) i).getOptionGroup() + "\";";
            }
            if (i instanceof MeterConsumer) {
                topology += "\n" + "\"" + i.getGroup() + "\"" + "--" + "\"" + "consumer " + k++ + "\";";
            }
        }
        topology += "\n}";
        File f = new File(filename);
        try {
            FileWriter writer = new FileWriter(f);
            writer.write(topology);
            writer.close();
            return true;
        } catch (IOException ex) {
            System.out.print("Can't save topology! Path is " + filename);
            ex.printStackTrace();
            return false;
        }
    }
}
