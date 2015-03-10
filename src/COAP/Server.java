package COAP;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.Resource;

/**
 *
 * @author Даниил
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
    @Override
    protected Resource createRoot(){
        if(id==-1 || id==0)
            return new DescriptionResource(_id);
        else return new DescriptionResource(id);
    }
}
