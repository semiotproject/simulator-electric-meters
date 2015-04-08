package ru.semiot.simulator.electricmeter.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import madkit.kernel.Agent;
import madkit.kernel.Madkit;
import ru.semiot.simulator.electricmeter.EnergyOrganization;
import ru.semiot.simulator.electricmeter.Meter;
import ru.semiot.simulator.electricmeter.MeterConsumer;
import ru.semiot.simulator.electricmeter.MeterMiddle;
import ru.semiot.simulator.electricmeter.MeterOrigin;
import static ru.semiot.simulator.electricmeter.utils.Config.conf;
import static ru.semiot.simulator.electricmeter.utils.Generator.generateSerial;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class LauncherSimulation extends Agent{

    private static final ArrayList<Meter> agentsList = new ArrayList<>();

    public static void main(String args[]) {
        if (args.length >= 1 && !args[0].isEmpty()) {
            if (conf.setConfigFromFile(args[0])) {
                System.out.println("Data loaded");
            } else {
                System.out.println("Error. Data didn't load!");
            }
        } else {
            System.out.println("Start with default data");
        }
        conf.writeConfigToFile("config.xml");
        new Madkit(Madkit.Option.launchAgents.toString(), LauncherSimulation.class.getName() + "," + false + ",1");
        if (args.length >= 2 && !args[1].isEmpty()) {
            getTopology(args[1]);
        }
    }
    @Override
    protected void activate() {
        Meter meter = new MeterOrigin();
        meter.setMeter("mercury30", "mercury30", generateSerial());

        meter.setGroup(EnergyOrganization.METER_GROUP_ORIGIN);
        meter.setRole(EnergyOrganization.METER_ROLE_ORIGIN);

        agentsList.add(meter);
        for (int i = 0; i < conf.getNbOfAgentsMiddle(); i++) {
            meter = new MeterMiddle();
            meter.setMeter("mercury30", "mercury30", generateSerial());
            meter.setGroup(EnergyOrganization.METER_GROUP_ORIGIN);
            meter.setRole(EnergyOrganization.METER_ROLE_CONSUMER);
            ((MeterMiddle) meter).setOptionGroup(EnergyOrganization.METER_GROUP_OPTION + i);
            ((MeterMiddle) meter).setOptionRole(EnergyOrganization.METER_ROLE_ORIGIN);
            agentsList.add(meter);
        }

        for (int i = 0; i < conf.getNbOfAgentsConsumer(); i++) {
            meter = new MeterConsumer();
            meter.setMeter("mercury30", "mercury30", generateSerial());
            meter.setGroup(EnergyOrganization.METER_GROUP_OPTION + (int) (conf.getNbOfAgentsMiddle() * Math.random()));
            meter.setRole(EnergyOrganization.METER_ROLE_CONSUMER);
            agentsList.add(meter);
        }
        
        agentsList.stream().forEach((i) -> {
            launchAgent(i, false);
        });
    }
	
	@Override
    protected void live() {
		System.out.println("All meters started");
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
                topology += "\n" + "\"" + i.getGroup() + "\"" + "--" + "\"" + i.getRole() + k++ + "\";";
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
            System.out.print("Can't save topology!");
            ex.printStackTrace();
            return false;
        }
    }
}
