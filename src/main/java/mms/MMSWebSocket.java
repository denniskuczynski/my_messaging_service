package mms;
 
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class MMSWebSocket implements MessagePublisherListener {
    private Map<Session, Map<String, String>> _sessionData;
    private ObjectMapper _mapper;

    public MMSWebSocket() {
        _sessionData = new ConcurrentHashMap<>();
        MessagePublisher.getInstance().addListener(this);
        _mapper = Jackson.newObjectMapper();
    }

    @OnWebSocketConnect
    public void onConnect(final Session session) {
        System.out.println("onConnect");
        
        final Map<String,List<String>> parameters = session.getUpgradeRequest().getParameterMap();
        final Map<String, String> sessionData = new HashMap<>();
        if (parameters.get("groupId") != null && !parameters.get("groupId").isEmpty()) {
            sessionData.put("groupId", parameters.get("groupId").get(0));
        }
        _sessionData.put(session, sessionData);
    }

    @OnWebSocketMessage
    public void onMessage(final Session session, final String message) {
        System.out.println("onMessage: "+message);
    }

    @OnWebSocketClose
    public void onClose(final Session session, final int closeCode, final String closeReason) {
        System.out.println("onClose: "+closeReason);
        _sessionData.remove(session);
    }

    @OnWebSocketError
    public void onError(final Session session, final Throwable error) {
        System.out.println("onError: "+error);
        error.printStackTrace();
    }

    public void onPublish(final String pGroupId, final Map pMessage) {
        System.out.println("onPublish: "+pMessage);
        _sessionData.entrySet().stream().forEach(entry -> {
            final Session session = entry.getKey();
            final Map<String, String> sessionData = entry.getValue();
            if (pGroupId.equals(sessionData.get("groupId"))) {
                try {
                    session.getRemote().sendString(_mapper.writeValueAsString(pMessage), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
