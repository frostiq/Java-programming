package Bazhanau.Task3.Messages;

import java.io.Serializable;
import java.util.Map;

public abstract class MessageModel implements Serializable {
    public enum MessageHeader {
        EXECUTE, LIST_DIR
    }

    public MessageHeader Header;

    public Map<String, Object> Body;
}
