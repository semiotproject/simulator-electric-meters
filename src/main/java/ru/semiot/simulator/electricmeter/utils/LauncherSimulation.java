package ru.semiot.simulator.electricmeter.utils;

import ru.semiot.simulator.electricmeter.coap.CoAP;
import ru.semiot.simulator.electricmeter.EnergyOrganization;
import ru.semiot.simulator.electricmeter.MeterConsumer;
import ru.semiot.simulator.electricmeter.MeterMiddle;
import ru.semiot.simulator.electricmeter.MeterOrigin;
import java.util.ArrayList;
import madkit.kernel.Agent;
import static ru.semiot.simulator.electricmeter.utils.Generator.generateDate;
import static ru.semiot.simulator.electricmeter.utils.Generator.generateSerial;
import madkit.kernel.Madkit;
import ru.semiot.simulator.electricmeter.Meter;
import static ru.semiot.simulator.electricmeter.utils.Config.conf;
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
    private static ru.semiot.simulator.electricmeter.coap.CoAP server;
    public static boolean exit = false;
    //private static int[]pause;
    //private static int count=0;
    
    //public static int getPause(){
    //    return pause[count++];
    //}

    @Override
    protected void activate() {
        ///pause=new int[conf.getNbOfAgentsMiddle()+conf.getNbOfAgentsConsumer()];
        //for(int i=0;i<pause.length;i++)
          //  pause[i]=500+500*i;
        server = new CoAP();
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
            System.out.print("Can't save topology!");
            ex.printStackTrace();
        }
    }
    public static void main(String args[]) {
        if(args.length>1 && !args[0].isEmpty()){
            conf.setConfigFromFile(args[1]);
        }
        LauncherSimulation.startSimulator(false);
        if(args.length>2 && !args[1].isEmpty())
            getTopology(args[2]);        
    }
}
