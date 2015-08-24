package mms;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import mms.resources.MessageResource;

import java.util.ArrayList;
import java.util.Map;

public class MessagePublisher {

    private static MessagePublisher _instance;

    private final ArrayList<MessagePublisherListener> _listeners;

    private MessagePublisher() {
        _listeners = new ArrayList<>();
    }

    public static MessagePublisher getInstance() {
        if (_instance == null) {
            _instance = new MessagePublisher();
        }
        return _instance;
    }

    public void publish(final String pGroupId, final Map pMessage) {
        for (MessagePublisherListener listener : _listeners) {
            listener.onPublish(pGroupId, pMessage);
        }
    }

    public void addListener(final MessagePublisherListener listener) {
        _listeners.add(listener);
    }

}
