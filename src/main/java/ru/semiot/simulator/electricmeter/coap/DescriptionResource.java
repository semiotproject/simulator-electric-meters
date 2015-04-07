package ru.semiot.simulator.electricmeter.coap;

import org.eclipse.californium.core.CoapResource;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CONTENT;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_HTML;
import org.eclipse.californium.core.server.resources.CoapExchange;
import static ru.semiot.simulator.electricmeter.utils.Config.conf;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class DescriptionResource extends CoapResource {

    private final int port;
    private final String desccription;

    public DescriptionResource(int port) {
        super("meter");
        this.port = port;
        this.desccription = ("@prefix ssn: <http://purl.oclc.org/NET/ssnx/ssn#> .\n"
                + "@prefix emtr: <http://purl.org/NET/ssnext/electricmeters#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "@prefix ssncom: <http://purl.org/NET/ssnext/communication#> .\n"
                + "\n"
                + "<coap://${HOST}:${PORT}/meter> a emtr:ElectricMeter ;\n"
                + "    ssn:hasSubsystem <coap://${HOST}:${PORT}/meter/amperage> ;\n"
                + "    ssn:hasSubsystem <coap://${HOST}:${PORT}/meter/voltage> ;\n"
                + "    ssn:hasSubsystem <coap://${HOST}:${PORT}/meter/power> ;\n"
                + "    rdfs:label \"Electric Meter #${PORT}\" .\n"
                + "\n"
                + "<coap://${HOST}:${PORT}/meter/amperage> a ssn:Sensor ;\n"
                + "    ssn:observes emtr:Amperage ;\n"
                + "    ssncom:hasCommunicationEndpoint <coap://${HOST}:${PORT}/meter/amperage/obs> .\n"
                + "\n"
                + "<coap://${HOST}:${PORT}/meter/voltage> a ssn:Sensor ;\n"
                + "    ssn:observes emtr:Voltage ;\n"
                + "    ssncom:hasCommunicationEndpoint <coap://${HOST}:${PORT}/meter/voltage/obs> .\n"
                + "\n"
                + "<coap://${HOST}:${PORT}/meter/power> a ssn:Sensor ;\n"
                + "    ssn:observes emtr:Power ;\n"
                + "    ssncom:hasCommunicationEndpoint <coap://${HOST}:3131/meter/power/obs> .\n"
                + "\n"
                + "<coap://${HOST}:${PORT}/meter/amperage/obs> a ssncom:CommunicationEndpoint ;\n"
                + "    ssncom:protocol \"COAP\" .\n"
                + "<coap://${HOST}:${PORT}/meter/voltage/obs> a ssncom:CommunicationEndpoint ;\n"
                + "    ssncom:protocol \"COAP\" .\n"
                + "<coap://${HOST}:${PORT}/meter/power/obs> a ssncom:CommunicationEndpoint ;\n"
                + "    ssncom:protocol \"COAP\" .")
                .replace("${PORT}", Integer.toString(this.port))
                .replace("${HOST}", conf.getHostName());
    }

    public String getDescription() {
        return desccription;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(CONTENT, desccription, TEXT_HTML);
    }

}
