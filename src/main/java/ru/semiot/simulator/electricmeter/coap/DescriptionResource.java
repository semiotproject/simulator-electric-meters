package ru.semiot.simulator.electricmeter.coap;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.eclipse.californium.core.CoapResource;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CONTENT;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_HTML;
import org.eclipse.californium.core.server.resources.CoapExchange;
import static ru.semiot.simulator.electricmeter.utils.SimulatorConfig.conf;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class DescriptionResource extends CoapResource {

    private final int port;
    private String description;

    public DescriptionResource(int port) {
        super("meter");
        this.port = port;
        try {
            this.description = IOUtils.toString(DescriptionResource.class.getResourceAsStream(
                    "/ru/semiot/simulator/electricmeter/description.ttl"));			
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
		this.description = this.description
                .replace("${PORT}", Integer.toString(this.port))
                .replace("${HOST}", conf.getHostName());		
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(CONTENT, description, TEXT_HTML);
    }

}
