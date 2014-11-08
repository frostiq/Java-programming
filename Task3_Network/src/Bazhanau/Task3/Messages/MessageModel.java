package Bazhanau.Task3.Messages;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MessageModel implements Serializable {
    private static Random random = new Random();
    public int id = random.nextInt();
    public MessageType type;
    public MessageHeader header;
    public MessageModel request;
    public Map<String, Object> body = new HashMap<>();

    public static enum MessageHeader {
        EXECUTE, LIST_DIR
    }

    public static enum MessageType {
        REQUEST, RESPONSE
    }
}
