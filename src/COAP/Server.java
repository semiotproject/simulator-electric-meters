/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COAP;

import java.net.SocketException;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.config.NetworkConfig;
import static Utils.Config.conf;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Даниил
 */
public class Server extends CoapServer implements IListener {

    private static final Map<String, SubscribeSingle> handlers = new HashMap<>();
    private final SubscribeHub hub = new SubscribeHub();
    private static final TestimonialStore store = TestimonialStore.getInstance();

    @Override
    public void onCreated(String _id) {
        System.out.println("New meter registered and available on /subscribe/" + _id);
        registerHandler(_id);
    }

    @Override
    public void onUpdated(String id, String data) {
        System.out.println("Meter with id " + id + " updated; Data " + data);
        if (handlers.containsKey(id)) {
            handlers.get(id).changed();
        }
    }

    private void registerHandler(String id) {
        SubscribeSingle s = new SubscribeSingle(id);
        handlers.put(id, s);
        hub.add(s);
    }

    public Server() throws SocketException {
        NetworkConfig.getStandard()
                .setInt(NetworkConfig.Keys.MAX_MESSAGE_SIZE, 64)
                .setInt(NetworkConfig.Keys.PREFERRED_BLOCK_SIZE, 64)
                .setInt(NetworkConfig.Keys.NOTIFICATION_CHECK_INTERVAL_COUNT, 4)
                .setInt(NetworkConfig.Keys.NOTIFICATION_CHECK_INTERVAL_TIME, 30000)
                .setString(NetworkConfig.Keys.HEALTH_STATUS_PRINT_LEVEL, "INFO");

        add(hub);
        store.addListener(this);
        this.start();
        System.out.println(Server.class.getSimpleName() + " listening on port " + conf.getCoapPort());
    }
}
