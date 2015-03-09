package Utils;

import COAP.Server;
import Meter.EnergyOrganization;
import Meter.MeterConsumer;
import Meter.MeterMiddle;
import Meter.MeterOrigin;
import java.util.ArrayList;
import madkit.kernel.Agent;
import static Utils.Generator.generateDate;
import static Utils.Generator.generateSerial;
import madkit.kernel.Madkit;
import Meter.Meter;
import static Utils.Config.conf;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Даниил
 */
public class LauncherSimulation extends Agent {

    private static boolean haveGUI = true;
    private static ArrayList<Meter> agentsList = new ArrayList<>();
    private static COAP.Server server;
    public static boolean exit = false;

    @Override
    protected void activate() {
        Meter meter = new MeterOrigin();
        meter.setMeter("mercury30", "mercury30", generateDate(), generateSerial());

        meter.setGroup(EnergyOrganization.METER_GROUP_ORIGIN);
        meter.setRole(EnergyOrganization.METER_ROLE_ORIGIN);

        agentsList.add(meter);
        for (int i = 0; i < conf.getNbOfAgentsMiddle(); i++) {
            meter = new MeterMiddle();
            meter.setMeter("mercury30", "mercury30", generateDate(), generateSerial());
            meter.setGroup(EnergyOrganization.METER_GROUP_ORIGIN);
            meter.setRole(EnergyOrganization.METER_ROLE_CONSUMER);
            ((MeterMiddle) meter).setOptionGroup(EnergyOrganization.METER_GROUP_OPTION + i);
            ((MeterMiddle) meter).setOptionRole(EnergyOrganization.METER_ROLE_ORIGIN);
            agentsList.add(meter);
        }

        for (int i = 0; i < conf.getNbOfAgentsConsumer(); i++) {
            meter = new MeterConsumer();
            meter.setMeter("mercury30", "mercury30", generateDate(), generateSerial());
            meter.setGroup(EnergyOrganization.METER_GROUP_OPTION + (int) (conf.getNbOfAgentsMiddle() * Math.random()));
            meter.setRole(EnergyOrganization.METER_ROLE_CONSUMER);
            agentsList.add(meter);
        }
        try {
            server = new Server();
        } catch (SocketException ex) {
            Logger.getLogger(LauncherSimulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        agentsList.stream().forEach((i) -> {
            launchAgent(i, haveGUI);
        });
    }

    @Override
    protected void live() {
        while (!exit);
        end();
    }

    @Override
    protected void end() {
        pause(2000);
        agentsList.stream().forEach((i) -> i.stop());
        agentsList = null;

    }

    public static void startSimulator(boolean _haveGUI) {
        exit = false;
        haveGUI = _haveGUI;
        new Madkit(Madkit.Option.launchAgents.toString(), LauncherSimulation.class.getName() + "," + false + ",1");
    }

    public static void stopSimulator() {
        exit = true;
    }

    public static void getTopology(String filename) {
        String topology = "graph Topology{";
        String origin = "\"Origin\"";
        int k = 0;
        for (Meter i : agentsList) {
            if (i instanceof MeterMiddle) {
                topology += "\n" + origin + "--" + "\"" + ((MeterMiddle) i).getOptionGroup() + "\";";
            }
            if (i instanceof MeterConsumer) {
                topology += "\n" + "\"" + i.getGroup() + "\"" + "--" + "\"" + i.getRole() + k++ +"\";";
            }
        }
        topology += "\n}";
        File f = new File(filename);
        try {
            FileWriter writer = new FileWriter(f);
            writer.write(topology);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
