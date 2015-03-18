package ru.semiot.simulator.electricmeter.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Даниил
 */
public class Config {

    private final Map<String, String> data;
    public static final Config conf = new Config();

    private Config() {
        this.data = new HashMap<>();
        data.put("NumberOfMiddleMeter", Integer.toString(3));
        data.put("NumberOfConsumerMeter", Integer.toString(7));
        data.put("StartDelay", Integer.toString(3000));
        data.put("WorkDelay", Integer.toString(5000));
        data.put("AverageAmperageConsumer", Double.toString(6));
        data.put("DeviationAmperageConsumerPersent", Integer.toString(60));
        data.put("AverageVoltageOrigin", Double.toString(220));
        data.put("DeviationVoltageOriginPersent", Integer.toString(5));
        data.put("AverageResistanceOnWire", Double.toString(7.5));
        data.put("DeviationResistanceOnWirePersent", Integer.toString(33));
        data.put("CoapTTL", Integer.toString(900001));
        data.put("CoapPort", Integer.toString(7000));
    }

    private void setAgents(int nbOfAgentsMiddle, int nbOfAgentsConsumer) {
        data.put("NumberOfMiddleMeter", Integer.toString(nbOfAgentsMiddle));
        data.put("NumberOfConsumerMeter", Integer.toString(nbOfAgentsConsumer));
    }

    private void setDelay(int startPause, int generatePause) {
        data.put("StartDelay", Integer.toString(startPause));
        data.put("WorkDelay", Integer.toString(generatePause));
    }

    private void setValues(double amperage, int amperagePersent, double voltage, int voltagePersent, double resistance, int resistancePersent) {
        data.put("AverageAmperageConsumer", Double.toString(amperage));
        data.put("DeviationAmperageConsumerPersent", Integer.toString(amperagePersent));
        data.put("AverageVoltageOrigin", Double.toString(voltage));
        data.put("DeviationVoltageOriginPersent", Integer.toString(voltagePersent));
        data.put("AverageResistanceOnWire", Double.toString(resistance));
        data.put("DeviationResistanceOnWirePersent", Integer.toString(resistancePersent));
    }

    private void setServer(int CoapTTL, int CoapPort) {
        data.put("CoapTTL", Integer.toString(CoapTTL));
        data.put("CoapPort", Integer.toString(CoapPort));
    }

    public void setConfig(int nbOfAgentsMiddle, int nbOfAgentsConsumer,
            int startPause, int generatePause,
            double amperage, int amperagePersent, double voltage, int voltagePersent, double resistance, int resistancePersent,
            int CoapTTL, int CoapPort) {
        setAgents(nbOfAgentsMiddle, nbOfAgentsConsumer);
        setDelay(startPause, generatePause);
        setValues(amperage, amperagePersent, voltage, voltagePersent, resistance, resistancePersent);
        setServer(CoapTTL, CoapPort);
    }

    public boolean setConfigFromFile(String filename) {
        try {
            File file = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            for (String i : data.keySet()) {
                data.put(i, doc.getElementsByTagName(i).item(0).getTextContent());
            }
            return true;
        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeConfigToFile(String filename) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("config");
            List<Element> elements = new ArrayList<>();
            document.appendChild(root);

            for (String i : data.keySet()) {
                elements.add(document.createElement(i));
                elements.get(elements.size() - 1).setTextContent(data.get(i));
                root.appendChild(elements.get(elements.size() - 1));
            }
            document.normalizeDocument();
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
            return true;
        } catch (ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public int getNbOfAgentsMiddle() {
        return Integer.parseInt(data.get("NumberOfMiddleMeter"));
    }

    public int getNbOfAgentsConsumer() {
        return Integer.parseInt(data.get("NumberOfConsumerMeter"));
    }

    public int getStartDelay() {
        return Integer.parseInt(data.get("StartDelay"));
    }

    public int getWorkDelay() {
        return Integer.parseInt(data.get("WorkDelay"));
    }

    public int getDeviationAmperagePersent() {
        return Integer.parseInt(data.get("DeviationAmperageConsumerPersent"));
    }

    public int getDeviationVoltagePersent() {
        return Integer.parseInt(data.get("DeviationVoltageOriginPersent"));
    }

    public int getDeviationResistanceOnWirePersent() {
        return Integer.parseInt(data.get("DeviationResistanceOnWirePersent"));
    }

    public double getAverageAmperageConsumer() {
        return Double.parseDouble(data.get("AverageAmperageConsumer"));
    }

    public double getAverageVoltageOrigin() {
        return Double.parseDouble(data.get("AverageVoltageOrigin"));
    }

    public double getAverageResistanceOnWire() {
        return Double.parseDouble(data.get("AverageResistanceOnWire"));
    }

    public int getCoapTTL() {
        return Integer.parseInt(data.get("CoapTTL"));
    }

    public int getCoapPort() {
        return Integer.parseInt(data.get("CoapPort"));
    }

}
