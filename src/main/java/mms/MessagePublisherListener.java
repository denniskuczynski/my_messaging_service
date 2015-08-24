package mms;

import java.util.Map;

public interface MessagePublisherListener {
    public void onPublish(final String pGroupId, final Map pMessage);
}
