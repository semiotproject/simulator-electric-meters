package COAP;

/**
 *
 * @author Даниил
 */
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.DELETED;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.TEXT_PLAIN;
import static Utils.Config.conf;

/**
 * Created by nikolay on 19.02.15.
 */
public class SubscribeSingle extends CoapResource {

    private static final TestimonialStore data = TestimonialStore.getInstance();

    private String id;

    public SubscribeSingle(String id) {
        super(id);

        this.id = id;

        setObservable(true);
        getAttributes().setTitle("Observer of the meter with id " + id);
        getAttributes().addResourceType("observe");
        getAttributes().setObservable();
        setObserveType(CoAP.Type.CON);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(conf.getCoapTTL());
        exchange.respond(CONTENT, data.getData(id), TEXT_PLAIN);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        clearAndNotifyObserveRelations(NOT_FOUND);
        exchange.respond(DELETED);
        //timer.cancel();
    }

}
