package ru.semiot.simulator.electricmeter.coap;

import ru.semiot.simulator.electricmeter.utils.StateData;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.californium.core.CoapResource;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CONTENT;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_PLAIN;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 *
 * @author Даниил
 */
public class VoltageResource extends CoapResource {

    private final int id;
    private final int port;

    public VoltageResource(int port, int id) {
        super("voltage");
        this.id = id;
        this.port = port;

        setObservable(true);
        getAttributes().setTitle("Endpoint for voltage testimonial ");
        getAttributes().addResourceType("observe");
        getAttributes().setObservable();
        setObserveType(org.eclipse.californium.core.coap.CoAP.Type.CON);
    }

    private String toTurtle(double voltage, long timestamp) {
        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(timestamp * 1000));

        return "@prefix hmtr: <http://purl.org/NET/ssnext/electricmeters#>\n"
                + "@prefix meter: <http://purl.org/NET/ssnext/meters/core#>\n"
                + "\n"
                + String.format("<#voltage-%s-%d> a hmtr:VoltageObservation ;\n", id, timestamp)
                + String.format("    ssn:observationResultTime “%s”^^xsd:dateTime ;\n", date)
                + String.format("    ssn:observedBy <%s> ;\n", "localhost:" + Integer.toString(port))
                + String.format("    ssn:observationResult <#voltage-%s-%d-%.2f> .\n", id, timestamp, voltage)
                + "\n"
                + String.format("<#voltage-%s-%d-results> a hmtr:VoltageSensorOutput ;\n", id, timestamp)
                + String.format("    ssn:isProducedBy <%s> ;\n", "localhost:" + Integer.toString(port))
                + String.format("    ssn:hasValue <#voltage-%s-%d-%.2f> .\n", id, timestamp, voltage)
                + "\n"
                + String.format("<#voltage-%s-%d-resultvalue> a hmtr:VoltageValue ;\n", id, timestamp)
                + String.format("    meter:hasQuantityValue “%.3f”^^xsd:float", voltage);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(5);
        StateData t = TestimonialStore.getInstance().getData(id);
        exchange.respond(CONTENT, toTurtle(t.getVoltage(), t.getTime()), TEXT_PLAIN);
    }
}
