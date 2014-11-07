package Bazhanau.Task3.Dispatchers;

import Bazhanau.ICatcher;
import Bazhanau.Task3.Client.IClientWindow;
import Bazhanau.Task3.Messages.MessageModel;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientDispatcher extends AbstractDispatcher {
    protected IClientWindow clientWindow;

    public ClientDispatcher(Socket socket, ICatcher catcher, IClientWindow clientWindow) {
        super(socket, catcher);
        this.sendDisconnectMessage = false;
        this.clientWindow = clientWindow;
    }

    public void sendCommand(String command) throws IOException {
        MessageModel message = new MessageModel();
        message.type = MessageModel.MessageType.REQUEST;
        message.header = MessageModel.MessageHeader.EXECUTE;
        message.body.put("command", command);
        this.sendMessage(message);
    }

    public void sendDirRequest(String rootPath) throws IOException {
        MessageModel message = new MessageModel();
        message.type = MessageModel.MessageType.REQUEST;
        message.header = MessageModel.MessageHeader.LIST_DIR;
        message.body.put("root_path", rootPath);
        this.sendMessage(message);
    }

    @Override
    public synchronized void destroyDispatcher() {
        this.clientWindow.closeConnection();
    }

    @Override
    protected MessageModel dispatch(MessageModel message) {
        try {
            if (message.type != MessageModel.MessageType.RESPONSE) {
                throw new IOException("Invalid message type");
            }

            clientWindow.printToLog(message.header.toString() + ": " + message.body.get("status"));

            if (message.body.get("status").equals("Ok")) {
                switch (message.header) {
                    case EXECUTE:
                        break;
                    case LIST_DIR:
                        for (String fileName : (ArrayList<String>) message.body.get("file_names")) {
                            clientWindow.printToLog(fileName);
                        }
                        break;
                    default:
                        throw new IOException("Invalid message header");
                }
            }
        } catch (IOException e) {
            catcher.catchException(e);
        }
        return null;
    }
}
