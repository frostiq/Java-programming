package Bazhanau.Task3.Dispatchers;

import Bazhanau.Task3.Messages.MessageModel;
import Bazhanau.Task3.Messages.RequestMessageModel;
import Bazhanau.Task3.Messages.ResponseMessageModel;
import Bazhanau.Task3.Server.ServerBeacon;

import java.io.IOException;
import java.net.Socket;

public class ServerDispatcher extends AbstractDispatcher {
    private ServerBeacon serverBeacon;

    public ServerDispatcher(Socket socket, ServerBeacon serverBeacon) {
        super(socket, serverBeacon.getServerWindow().getCatcher());
        this.serverBeacon = serverBeacon;
    }

    @Override
    protected void destroyDispatcher() {
        serverBeacon.disconnectSocket(this);
    }

    @Override
    protected ResponseMessageModel dispatch(MessageModel message) {
        ResponseMessageModel response = new ResponseMessageModel();
        response.Header = message.Header;
        try {
            if (!(message instanceof RequestMessageModel)) {
                throw new IOException("Invalid message type");
            }

            switch (message.Header) {
                case EXECUTE:
                    String command = (String) message.Body.get("command");
                    Runtime.getRuntime().exec(command);
                    response.Body.put("status", "Ok");
                    break;
                case LIST_DIR:
                    response.Body.put("status", "Ok");
                    break;
                default:
                    throw new IOException("Invalid message header");
            }
        } catch (IOException e) {
            serverBeacon.getServerWindow().printToLog(e.getMessage());
            response.Body.put("status", "Error: " + e.getMessage());
        }

        return response;
    }
}
