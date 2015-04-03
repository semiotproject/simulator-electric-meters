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
public class AmperageResource extends CoapResource {

    private final int id;
    private final int port;

    public AmperageResource(int port, int id) {
        super("amperage");
        this.id = id;
        this.port = port;

        setObservable(true);
        getAttributes().setTitle("Endpoint for amperage testimonial ");
        getAttributes().addResourceType("observe");
        getAttributes().setObservable();
        setObserveType(org.eclipse.californium.core.coap.CoAP.Type.CON);
    }

    private String toTurtle(double amperage, long timestamp) {
        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(timestamp * 1000));

        return "@prefix emtr: <http://purl.org/NET/ssnext/electricmeters#>\n"
                + "@prefix meter: <http://purl.org/NET/ssnext/meters/core#>\n"
                + "\n"
                + String.format("<#amperage-%s-%d> a emtr:AmperageObservation ;\n", id, timestamp)
                + String.format("    ssn:observationResultTime “%s”^^xsd:dateTime ;\n", date)
                + String.format("    ssn:observedBy <%s> ;\n", "localhost:" + Integer.toString(port))
                + String.format("    ssn:observationResult <#amperage-%s-%d-result> .\n", id, timestamp)
                + "\n"
                + String.format("<#amperage-%s-%d-results> a emtr:AmperageSensorOutput ;\n", id, timestamp)
                + String.format("    ssn:isProducedBy <%s> ;\n", "localhost:" + Integer.toString(port))
                + String.format("    ssn:hasValue <#amperage-%s-%d-resultvalue> .\n", id, timestamp)
                + "\n"
                + String.format("<#amperage-%s-%d-resultvalue> a emtr:AmperageValue ;\n", id, timestamp)
                + String.format("    meter:hasQuantityValue “%.3f”^^xsd:float", amperage);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(5);
        StateData t = TestimonialStore.getInstance().getData(id);
        exchange.respond(CONTENT, toTurtle(t.getAmperage(), t.getTime()), TEXT_PLAIN);
    }
}
