package ru.semiot.simulator.electricmeter.coap;

import org.eclipse.californium.core.CoapResource;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CONTENT;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_HTML;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 *
 * @author Даниил
 */
public class DescriptionResource extends CoapResource {

    private final int id;
    private final String text;

    public DescriptionResource(int id) {
        super("desc");
        this.id = id;
        text = "@prefix emtr: <http://purl.org/NET/ssnext/electricmeters#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "<http://example.com/resources/#meter-" + Integer.toString(this.id) + "> a emtr:ElectricMeter;\n"
                + "rdfs:label \"Electric_Meter_№_"+ Integer.toString(this.id) +"\".";
        getAttributes().setTitle(text);
    }
    
    public String getDescription(){
        return text;
    }
    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(5);
        exchange.respond(CONTENT, text, TEXT_HTML);
    }

}
