package COAP;

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
        super("");
        this.id = id;
        text = "@prefix emtr: <http://purl.org/NET/ssnext/electricmeters#>\n"
                + "\n"
                + "<#meter-" + Integer.toString(this.id) + "> a emtr:ElectricMeter .";
        getAttributes().setTitle(text);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(5);
        exchange.respond(CONTENT, text, TEXT_HTML);
    }

}
