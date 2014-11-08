package Bazhanau.Task3.Dispatchers;

import Bazhanau.ICatcher;
import Bazhanau.Task3.Client.IClientWindow;
import Bazhanau.Task3.Messages.MessageModel;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ClientDispatcher extends AbstractDispatcher {
    protected IClientWindow clientWindow;
    protected ConcurrentHashMap<Integer, MessageModel> requestResponseMap = new ConcurrentHashMap<>();

    public ClientDispatcher(Socket socket, ICatcher catcher, IClientWindow clientWindow) {
        super(socket, catcher);
        this.clientWindow = clientWindow;
    }

    protected MessageModel sendRequest(MessageModel request) throws IOException {
        this.sendMessage(request);
        requestResponseMap.put(request.id, request);
        try {
            request.wait();
        } catch (InterruptedException e) {
            catcher.catchException(e);
        }
        MessageModel response = requestResponseMap.get(request.id);
        requestResponseMap.remove(request.id);

        return response;
    }

    public void sendExecRequest(String command) throws IOException {
        MessageModel message = new MessageModel();
        message.type = MessageModel.MessageType.REQUEST;
        message.header = MessageModel.MessageHeader.EXECUTE;
        message.body.put("command", command);
        this.sendRequest(message);
    }

    public ArrayList<String> sendDirRequest(String rootPath) throws IOException {
        MessageModel message = new MessageModel();
        message.type = MessageModel.MessageType.REQUEST;
        message.header = MessageModel.MessageHeader.LIST_DIR;
        message.body.put("root_path", rootPath);
        message = this.sendRequest(message);
        return (ArrayList<String>) message.body.get("file_names");
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
                    case LIST_DIR:
                        MessageModel lock = requestResponseMap.get(message.request.id);
                        requestResponseMap.replace(message.request.id, message);
                        synchronized (lock) {
                            lock.notify();
                        }
                        break;
                    default:
                        throw new IOException("Invalid message header");
                }
            } else {
                throw new RuntimeException("response status: " + message.body.get("status"));
            }
        } catch (IOException e) {
            catcher.catchException(e);
        }
        return null;
    }

    public ICatcher getCatcher() {
        return clientWindow.getCatcher();
    }
}
