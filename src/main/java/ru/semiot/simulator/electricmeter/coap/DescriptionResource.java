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
    private final String desccription;

    public DescriptionResource(int port) {
        super("meter");
        this.port = port;
        try {
            this.desccription = IOUtils.toString(DescriptionResource.class.getResourceAsStream(
                    "/ru/semiot/simulator/electricmeter/description.ttl"));
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public String getDescription() {
        return desccription
                .replace("${PORT}", Integer.toString(this.port))
                .replace("${HOST}", conf.getHostName());
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(CONTENT, desccription, TEXT_HTML);
    }

}
