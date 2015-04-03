package ru.semiot.simulator.electricmeter.coap;

import org.eclipse.californium.core.CoapServer;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class Server extends CoapServer{
    private int id=TestimonialStore.getID();
    private final int port;
    public static int _id;
    public Server(int port){
        super(port);
        this.id=_id;
        this.port=port;        
    }
}
