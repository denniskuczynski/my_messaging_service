package mms;
 
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

@WebSocket
public class MMSWebSocket {
    private Set<Session> _sessions;

    public MMSWebSocket() {
        _sessions = new HashSet<>();
    }

    @OnWebSocketConnect
    public void onConnect(final Session session) {
        System.out.println("onConnect");
        final Map<String,List<String>> parameters = session.getUpgradeRequest().getParameterMap();
        System.out.println(parameters);
        _sessions.add(session);
    }

    @OnWebSocketMessage
    public void onMessage(final Session session, final String message) {
        System.out.println("onMessage: "+message);
    }

    @OnWebSocketClose
    public void onClose(final Session session, final int closeCode, final String closeReason) {
        System.out.println("onClose: "+closeReason);
        _sessions.remove(session);
    }

    @OnWebSocketError
    public void onError(final Session session, final Throwable error) {
        System.out.println("onError: "+error);
        error.printStackTrace();
    }
}
