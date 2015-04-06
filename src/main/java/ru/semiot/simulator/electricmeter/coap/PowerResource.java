package ru.semiot.simulator.electricmeter.coap;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.californium.core.CoapResource;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CONTENT;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_PLAIN;
import org.eclipse.californium.core.server.resources.CoapExchange;
import static ru.semiot.simulator.electricmeter.utils.Config.conf;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class PowerResource extends CoapResource {

    private final int port;
    private double power;
    private long timestamp;
    private final String template = "@prefix emtr: <http://purl.org/NET/ssnext/electricmeters#> .\n"
            + "@prefix meter: <http://purl.org/NET/ssnext/meters/core#> .\n"
            + "@prefix ssn: <http://purl.oclc.org/NET/ssnx/ssn#> .\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
            + "@prefix meter: <http://purl.org/NET/ssnext/meters/core#> .\n"
            + "@prefix : <coap://${HOST}:${PORT}/meter/power#> .\n"
            + "\n"
            + ":${TIMESTAMP} a emtr:PowerObservation ;\n"
            + "    ssn:observationResultTime \"${DATETIME}\"^^xsd:dateTime ;\n"
            + "    ssn:observedBy <coap://${HOST}:${PORT}/meter> ;\n"
            + "    ssn:observationResult :${TIMESTAMP}-result .\n"
            + "\n"
            + ":${TIMESTAMP}-result a emtr:PowerSensorOutput ;\n"
            + "    ssn:isProducedBy <coap://${HOST}:${PORT}/meter> ;\n"
            + "    ssn:hasValue :${TIMESTAMP}-resultvalue .\n"
            + "\n"
            + ":${TIMESTAMP}-resultvalue a emtr:PowerValue ;\n"
            + "    meter:hasQuantityValue \"${VALUE}\"^^xsd:float .";

    public PowerResource(int port) {
        super("obs");

        this.port = port;

        setObservable(true);
        getAttributes().setObservable();
    }

    public void setValues(double power, long timestamp) {
        this.power = power;
        this.timestamp = timestamp;
    }

    private String toTurtle(double power, long timestamp) {
        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(timestamp * 1000));

        return template
                .replace("${TIMESTAMP}", String.valueOf(timestamp))
                .replace("${DATETIME}", date)
                .replace("${HOST}", conf.getHostName())
                .replace("${PORT}", String.valueOf(this.port))
                .replace("${VALUE}", String.valueOf(power));
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(CONTENT, toTurtle(power, timestamp), TEXT_PLAIN);
    }
}
