package ru.semiot.simulator.electricmeter.coap;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.californium.core.CoapResource;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CONTENT;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_PLAIN;
import org.eclipse.californium.core.server.resources.CoapExchange;
import ru.semiot.simulator.electricmeter.utils.StateData;

/**
 *
 * @author Даниил
 */
public class PowerResource extends CoapResource {

    private final int id;
    private final int port;

    public PowerResource(int port, int id) {
        super("power");
        this.id = id;
        this.port = port;

        setObservable(true);
        getAttributes().setTitle("Endpoint for power testimonial ");
        getAttributes().addResourceType("observe");
        getAttributes().setObservable();
        setObserveType(org.eclipse.californium.core.coap.CoAP.Type.CON);
    }

    private String toTurtle(double power, long timestamp) {
        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(timestamp * 1000));

        return "@prefix emtr: <http://purl.org/NET/ssnext/electricmeters#>\n"
                + "@prefix meter: <http://purl.org/NET/ssnext/meters/core#>\n"
                + "\n"
                + String.format("<#power-%s-%d> a emtr:PowerObservation ;\n", id, timestamp)
                + String.format("    ssn:observationResultTime “%s”^^xsd:dateTime ;\n", date)
                + String.format("    ssn:observedBy <%s> ;\n", "localhost:" + Integer.toString(port))
                + String.format("    ssn:observationResult <#power-%s-%d-result> .\n", id, timestamp)
                + "\n"
                + String.format("<#power-%s-%d-results> a emtr:PowerSensorOutput ;\n", id, timestamp)
                + String.format("    ssn:isProducedBy <%s> ;\n", "localhost:" + Integer.toString(port))
                + String.format("    ssn:hasValue <#power-%s-%d-resultvalue> .\n", id, timestamp)
                + "\n"
                + String.format("<#power-%s-%d-resultvalue> a emtr:PowerValue ;\n", id, timestamp)
                + String.format("    meter:hasQuantityValue “%.3f”^^xsd:float", power);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(5);
        StateData t = TestimonialStore.getInstance().getData(id);
        exchange.respond(CONTENT, toTurtle(t.getPower(), t.getTime()), TEXT_PLAIN);
    }
}
