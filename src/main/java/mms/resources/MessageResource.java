package mms.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.Map;
import java.util.HashMap;

@Path("/mms")
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
    
    public MessageResource() {
    }

    @POST
    public Map publishMessage(final Map pMessage) {
        System.out.println("recieved pMessage");
        return new HashMap();
    }
}