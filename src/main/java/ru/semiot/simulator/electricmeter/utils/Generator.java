package ru.semiot.simulator.electricmeter.utils;

import java.util.ArrayList;
import java.util.List;
import static ru.semiot.simulator.electricmeter.utils.SimulatorConfig.conf;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class Generator {
    private final static List<Integer> serials = new ArrayList<>();
    public static String generateDate() {
        int day;
        int month;
        int year;
        do {
            day = (int) (Math.random() * 28);
        } while (day < 1);
        do {
            month = (int) (Math.random() * 12);
        } while (month < 1 || month > 12);
        do {
            year = (int) (Math.random() * 14) + 2000;
        } while (year > 2015);
        return "" + day + "." + month + "." + year;
    }

    public static String generateSerial() {
        int serial;
        do{
            serial=(int) (Math.random() * 100000);
        }
        while(!serials.isEmpty() && serials.contains(serial));
        serials.add(serial);
        return "" + serial;
    }

    public static double generateVoltage() {
        double volt;
        volt = conf.getAverageVoltageOrigin() + ((Math.random() > 0.5 ? (1) : (-1)) * (int) (Math.random() * (conf.getAverageVoltageOrigin() * conf.getDeviationVoltagePersent() / 100)));
        return volt;
    }

    public static double generateResistance() {
        double resist;
        resist = 75 + ((Math.random() > 0.5 ? (1) : (-1)) * (int) (Math.random() * 25));
        return resist;
    }

    public static double generateAmperage() {
        double amper;
        amper = conf.getAverageAmperageConsumer() + ((Math.random() > 0.5 ? (1) : (-1)) * (double) (Math.random() * (conf.getAverageAmperageConsumer() * conf.getDeviationAmperagePersent() / 100)));
        return amper;
    }

    public static double generateRisistanceOnWire() {
        double resist;
        resist = (conf.getAverageResistanceOnWire() + ((Math.random() > 0.5 ? (1) : (-1)) * (int) (Math.random() * (conf.getAverageResistanceOnWire() * conf.getDeviationResistanceOnWirePersent() / 100)))) / 10000;
        return resist;
    }

    public static double generateRisistanceOnWire(double amper) {
        double resist;
        resist = (conf.getAverageResistanceOnWire() + ((Math.random() > 0.5 ? (1) : (-1)) * (int) (Math.random() * (conf.getAverageResistanceOnWire() * conf.getDeviationResistanceOnWirePersent() / 100)))) / 10000;
        return resist;
    }
}
