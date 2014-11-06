import Bazhanau.Task3.Messages.MessageModel;
import com.google.gson.Gson;

public class GsonTest {
    public static void main(String[] args) {
        Gson gson = new Gson();
        MessageModel m = new MessageModel();
        m.type = MessageModel.MessageType.REQUEST;
        m.header = MessageModel.MessageHeader.EXECUTE;
        m.body.put("command", "cmd");
        String res = gson.toJson(m);
        System.out.println(res);
    }
}
