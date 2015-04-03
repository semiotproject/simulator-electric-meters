package ru.semiot.simulator.electricmeter.coap;

import org.eclipse.californium.core.CoapResource;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CONTENT;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_HTML;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class DescriptionResource extends CoapResource {

    private final int id;
    private final String desc;

    public DescriptionResource(int id) {
        super("desc");
        this.id = id;
        desc = ("@prefix ssn: <http://purl.oclc.org/NET/ssnx/ssn#> .\n"
                + "@prefix emtr: <http://purl.org/NET/ssnext/electricmeters#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "@prefix ssncom: <http://purl.org/NET/ssnext/communication#> .\n"
                + "\n"
                + "<coap://localhost:%s/meter> a emtr:ElectricMeter .\n"
                + "    ssn:hasSubsystem <coap://localhost:%s/meter/amperage> ;\n"
                + "    ssn:hasSubsystem <coap://localhost:%s/meter/voltage> ;\n"
                + "    ssn:hasSubsystem <coap://localhost:%s/meter/power> ;\n"
                + "    rdfs:label \"Electric_Meter_№_%s .\n"
                + "\n"
                + "<coap://localhost:%s/meter/amperage> a ssn:Sensor ;\n"
                + "    ssn:observes emtr:Amperage ;\n"
                + "    ssncom:hasCommunicationEndpoint <coap://localhost:%s/amperage/obs> ;\n"
                + "\n"
                + "<coap://localhost:%s/meter/voltage> a ssn:Sensor ;\n"
                + "    ssn:observes emtr:Voltage ;\n"
                + "    ssncom:hasCommunicationEndpoint <coap://localhost:%s/voltage/obs> ;    \n"
                + "\n"
                + "<coap://localhost:%s/meter/power> a ssn:Sensor ;\n"
                + "    ssn:observes emtr:Power ;\n"
                + "    ssncom:hasCommunicationEndpoint <coap://localhost:3131/power/obs> ;\n"
                + "    \n"
                + "\n"
                + "<coap://localhost:%s/meter/amperage/obs> a ssncom:CommunicationEndpoint ;\n"
                + "    ssncoom:protocol \"COAP\" .\n"
                + "<coap://localhost:%s/meter/voltage/obs> a ssncom:CommunicationEndpoint ;\n"
                + "    ssncoom:protocol \"COAP\" .\n"
                + "<coap://localhost:%s/meter/power/obs> a ssncom:CommunicationEndpoint ;\n"
                + "    ssncoom:protocol \"COAP\" .").replaceAll("%s", Integer.toString(this.id));
        getAttributes().setTitle(desc);
    }

    public String getDescription() {
        return desc;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(5);
        exchange.respond(CONTENT, desc, TEXT_HTML);
    }

}
