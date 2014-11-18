package Bazhanau.Task3.Server;

import Bazhanau.Network.AbstractDispatcher;
import Bazhanau.Network.MessageModel;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ServerDispatcher extends AbstractDispatcher {
    private ServerBeacon serverBeacon;

    public ServerDispatcher(Socket socket, ServerBeacon serverBeacon) {
        super(socket, serverBeacon.getServerWindow().getCatcher());
        this.serverBeacon = serverBeacon;
    }

    @Override
    public synchronized void destroyDispatcher() {
        serverBeacon.disconnectSocket(this);
    }

    @Override
    protected MessageModel dispatch(MessageModel message) {
        MessageModel response = new MessageModel();
        response.type = MessageModel.MessageType.RESPONSE;
        response.header = message.header;
        response.request = message;
        try {
            if (message.type != MessageModel.MessageType.REQUEST) {
                throw new IOException("Invalid message type");
            }

            switch (message.header) {
                case EXECUTE:
                    String command = (String) message.body.get("command");
                    Runtime.getRuntime().exec(command);
                    response.body.put("status", "Ok");
                    break;
                case LIST_DIR:
                    File root = new File((String) message.body.get("root_path"));
                    response.body.put("file_names", root.list());
                    response.body.put("status", "Ok");
                    break;
                default:
                    throw new IOException("Invalid message header");
            }
        } catch (IOException e) {
            serverBeacon.getServerWindow().printToLog(e.getMessage());
            response.body.put("status", "Error: " + e.getMessage());
        }

        return response;
    }
}
