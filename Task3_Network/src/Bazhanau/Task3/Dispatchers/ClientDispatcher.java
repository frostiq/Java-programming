package Bazhanau.Task3.Dispatchers;

import Bazhanau.ICatcher;
import Bazhanau.ILogWindow;
import Bazhanau.Task3.Messages.MessageModel;

import java.io.IOException;
import java.net.Socket;

public class ClientDispatcher extends AbstractDispatcher {
    protected ILogWindow logWindow;

    public ClientDispatcher(Socket socket, ICatcher catcher, ILogWindow logWindow) {
        super(socket, catcher);
        this.logWindow = logWindow;
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
        this.interrupt();
    }

    @Override
    protected MessageModel dispatch(MessageModel message) {
        try {
            if (message.type != MessageModel.MessageType.RESPONSE) {
                throw new IOException("Invalid message type");
            }

            logWindow.printToLog(message.header.toString() + ": " + message.body.get("status"));

            if (message.body.get("status").equals("Ok")) {
                switch (message.header) {
                    case EXECUTE:
                        break;
                    case LIST_DIR:
                        for (String fileName : (String[]) message.body.get("file_names")) {
                            logWindow.printToLog(fileName);
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
