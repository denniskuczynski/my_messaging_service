package mms.resources;

import mms.MessagePublisher;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.Map;
import java.util.HashMap;

@Path("/mms")
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
    
    @POST
    public Map publishMessage(final Map pMessage) {
        final String groupId = (String)pMessage.get("cid");
        MessagePublisher.getInstance().publish(groupId, pMessage);
        return new HashMap(); // return empty map for now
    }
}
