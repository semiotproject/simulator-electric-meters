package Utils;

import java.util.Calendar;

/**
 *
 * @author Даниил
 */
public class StateData {

    double Resistance;
    double Amperage;
    double Voltage;
    double Power;

    /**
     * Фукнция устанавливает значение аргументов
     *
     * @param amper Сила тока
     * @param volt Напряжение
     */
    public void setData(double amper, double volt) {
        Resistance = (double) Math.round(volt / amper * 1000) / 1000;
        Amperage = (double) Math.round(amper * 1000) / 1000;
        Voltage = (double) Math.round(volt * 1000) / 1000;
        Power = (double) Math.round(Amperage * Voltage * 1000) / 1000;
    }

    /**
     * Functions sets data from a string. All option data calculate
     * automatically
     *
     * @param data string contained data String must be next format:
     * ""Amperage":123,"Voltage":221".
     */
    public void setDataFromString(String data) {
        this.setData(Double.parseDouble(getValueFromString(data, "Amperage")),
                Double.parseDouble(getValueFromString(data, "Voltage")));
    }

    public String getStringData() {
        return "\"Amperage\":" + Amperage + ",\"Voltage\":" + Voltage;
    }

    @Override
    public String toString() {      
        return "\"Resistance\":" + Resistance + ",\"Amperage\":" + Amperage + ",\"Voltage\":" + Voltage + ",\"Power\":" + Power
                + "\"DateTime\":" + Calendar.getInstance().getTime().toString();
    }

    /**
     * Function returns value in string from string use parse
     *
     * @param parseString
     * @param str string for parsing
     * @return Double value in String
     */
    private String getValueFromString(String parseString, String str) {
        String value;
        int lastIndex = parseString.lastIndexOf(str) + str.length() + 2;
        value = parseString.substring(lastIndex);
        lastIndex = value.indexOf(',');
        if (lastIndex == -1) {
            return value;
        }

        value = value.substring(0, lastIndex);
        return value;
    }

    public String receiveAndSendData(double a, double v) {
        setData(a, v);
        return getStringData();
    }

    /**
     * Сложение показаний счетчиков Складываются сопротивления, остальное
     * расчитывается
     *
     * @param data
     * @return
     */
    public StateData addData(String data) {
        StateData d = createFromString(data);
        d.setData(this.Amperage + d.Amperage, this.Voltage);
        return d;
    }

    @Override
    public StateData clone() {
        StateData d = new StateData();
        d.setData(this.Amperage, this.Voltage);
        return d;
    }

    public static StateData createFromString(String str) {
        StateData d = new StateData();
        d.setDataFromString(str);
        return d;
    }

    public static StateData createFromValue(double amper, double volt) {
        StateData d = new StateData();
        d.setData(amper, volt);
        return d;
    }

    public double getAmperage() {
        return Amperage;
    }

    public double getPower() {
        return Power;
    }

    public double getResistance() {
        return Resistance;
    }

    public void setVoltage(double volt) {
        Resistance = (double) Math.round(volt / Amperage * 1000) / 1000;
        Voltage = (double) Math.round(volt * 1000) / 1000;
        Power = (double) Math.round(Amperage * Voltage * 1000) / 1000;
    }

    public void setAmperage(double amper) {
        Resistance = (double) Math.round(Voltage / amper * 1000) / 1000;
        Amperage = (double) Math.round(amper * 1000) / 1000;
        Power = (double) Math.round(Amperage * Voltage * 1000) / 1000;
    }

    public double getVoltage() {
        return Voltage;
    }
}
