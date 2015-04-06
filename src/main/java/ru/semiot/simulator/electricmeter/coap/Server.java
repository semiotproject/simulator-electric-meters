package ru.semiot.simulator.electricmeter.coap;

import java.util.Observable;
import java.util.Observer;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import static ru.semiot.simulator.electricmeter.utils.Config.conf;
import ru.semiot.simulator.electricmeter.utils.StateData;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class Server extends CoapServer implements Observer {

    private final AmperageResource amperage;
    private final VoltageResource voltage;
    private final PowerResource power;
    private final DescriptionResource description;

    public Server(int port) {
        super(port);
        amperage = new AmperageResource(port);
        voltage = new VoltageResource(port);
        power = new PowerResource(port);
        description = new DescriptionResource(port);

        add(description.add(
                new CoapResource("amperage").add(amperage),
                new CoapResource("voltage").add(voltage),
                new CoapResource("power").add(power)));
    }

    @Override
    public void start() {
        super.start();

        final CoapClient coapClient = new CoapClient(conf.getCoapRegister());
        coapClient.setEndpoint(getEndpoints().get(0));
        coapClient.post(description.getDescription(), MediaTypeRegistry.TEXT_PLAIN);
        coapClient.shutdown();
    }

    @Override
    public void update(Observable o, Object arg) {
        StateData data = (StateData) arg;
        amperage.setValues(data.getAmperage(), data.getTime());
        amperage.changed();

        voltage.setValues(data.getVoltage(), data.getTime());
        voltage.changed();

        power.setValues(data.getPower(), data.getTime());
        power.changed();
    }
}
