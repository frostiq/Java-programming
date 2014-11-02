package Bazhanau.Task3.Dispatchers;

import Bazhanau.ICatcher;
import Bazhanau.Task3.Messages.MessageModel;
import Bazhanau.Task3.Messages.RequestMessageModel;
import Bazhanau.Task3.Messages.ResponseMessageModel;

import java.io.IOException;
import java.net.Socket;

public class ClientDispatcher extends AbstractDispatcher {
    public ClientDispatcher(Socket socket, ICatcher catcher) {
        super(socket, catcher);
    }

    public void sendCommand(String command) throws IOException {
        RequestMessageModel message = new RequestMessageModel();
        message.Header = MessageModel.MessageHeader.EXECUTE;
        message.Body.put("command", command);
        this.sendMessage(message);
    }

    @Override
    protected void destroyDispatcher() {
        this.interrupt();
    }

    @Override
    protected ResponseMessageModel dispatch(MessageModel message) {
        return null;
    }
}
