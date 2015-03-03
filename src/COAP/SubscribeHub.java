package COAP;

import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import static Utils.Config.conf;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.DELETED;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_PLAIN;

/**
 *
 * @author Даниил
 */
public class SubscribeHub extends CoapResource {

    private static final TestimonialStore store = TestimonialStore.getInstance();

    private Timer timer;

    public SubscribeHub() {
        super("subscribe");

        setObservable(true);
        getAttributes().setTitle("Simple CoAP handler to observe third-party state change");
        getAttributes().addResourceType("observe");
        getAttributes().setObservable();
        setObserveType(CoAP.Type.CON);

        timer = new Timer();
        timer.schedule(new TimeTask(), 0, conf.getWorkDelay());
    }

    private class TimeTask extends TimerTask {

        @Override
        public void run() {
            changed();
        }
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(conf.getCoapTTL());
        exchange.respond(CONTENT, store.getData(), TEXT_PLAIN);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        clearAndNotifyObserveRelations(NOT_FOUND);
        exchange.respond(DELETED);
        timer.cancel();
    }

}
