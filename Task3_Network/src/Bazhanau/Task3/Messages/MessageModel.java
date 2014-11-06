package Bazhanau.Task3.Messages;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MessageModel implements Serializable {
    public static enum MessageHeader {
        EXECUTE, LIST_DIR
    }

    public static enum MessageType {
        REQUEST, RESPONSE
    }

    public MessageType type;

    public MessageHeader header;

    public MessageModel request;

    public Map<String, Object> body = new HashMap<>();
}
