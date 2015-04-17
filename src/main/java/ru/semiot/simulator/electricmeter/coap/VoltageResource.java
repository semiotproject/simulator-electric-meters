package ru.semiot.simulator.electricmeter.coap;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.eclipse.californium.core.CoapResource;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CONTENT;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_PLAIN;
import org.eclipse.californium.core.server.resources.CoapExchange;
import static ru.semiot.simulator.electricmeter.utils.SimulatorConfig.conf;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class VoltageResource extends CoapResource {

    private final int port;
    private double voltage;
    private long timestamp;
    private final String template;

    public VoltageResource(int port) {
        super("obs");
        this.port = port;

        setObservable(true);
        getAttributes().setObservable();

        try {
            this.template = IOUtils.toString(VoltageResource.class.getResourceAsStream(
                    "/ru/semiot/simulator/electricmeter/voltage.ttl"));
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public void setValues(double voltage, long timestamp) {
        this.voltage = voltage;
        this.timestamp = timestamp;
    }

    private String toTurtle(double voltage, long timestamp) {
        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(timestamp * 1000));

        return template
                .replace("${TIMESTAMP}", String.valueOf(timestamp))
                .replace("${DATETIME}", date)
                .replace("${HOST}", conf.getHostName())
                .replace("${PORT}", String.valueOf(this.port))
                .replace("${VALUE}", String.valueOf(voltage));
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(CONTENT, toTurtle(voltage, timestamp), TEXT_PLAIN);
    }
}
