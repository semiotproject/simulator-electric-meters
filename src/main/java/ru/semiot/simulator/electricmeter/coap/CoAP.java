package ru.semiot.simulator.electricmeter.coap;

import static ru.semiot.simulator.electricmeter.utils.Config.conf;
import ru.semiot.simulator.electricmeter.utils.StateData;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

/**
 *
 * @author Даниил
 */
public class CoAP implements IListener {

    private static final Map<Integer, List<CoapResource>> handlers = new HashMap<>();
    private int port = conf.getCoapPort();
    private CoapClient notifier;

    @Override
    public void onCreated(int _id) {
        Server._id=_id;
        Server server = new Server(port);
        CoapResource voltage = new VoltageResource(port, _id);
        CoapResource amperage = new AmperageResource(port, _id);
        CoapResource power = new PowerResource(port, _id);
        CoapResource description = new DescriptionResource(_id);
        server.add(voltage);
        server.add(amperage);
        server.add(power);
        server.add(description);
        
        server.start();
        
        handlers.put(_id, Arrays.asList(voltage,amperage,power));
        System.out.println("New meter registered and available on localhost:" + Integer.toString(port++));
        notifier.post(((DescriptionResource)description).getDescription(), MediaTypeRegistry.TEXT_PLAIN);
    }

    @Override
    public void onUpdated(int id, StateData data) {
        System.out.println("Meter with id " + id + " updated; " + data.getStringData());
        if (handlers.containsKey(id)) {
            for (CoapResource resource : handlers.get(id)) {
                resource.changed();
            }
        }
    }


    public CoAP() {
       TestimonialStore.getInstance().addListener(this);
       notifier = new CoapClient(conf.getCoapRegister());
    }
}
