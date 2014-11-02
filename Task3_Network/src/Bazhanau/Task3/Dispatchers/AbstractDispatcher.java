package Bazhanau.Task3.Dispatchers;

import Bazhanau.ICatcher;
import Bazhanau.Task3.Messages.MessageModel;
import Bazhanau.Task3.Messages.ResponseMessageModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public abstract class AbstractDispatcher extends Thread {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ICatcher catcher;

    protected AbstractDispatcher(Socket socket, ICatcher catcher) {
        this.socket = socket;
        this.catcher = catcher;
        this.setName(this.getClass().getName());
        try {
            InputStream ddd = socket.getInputStream();
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            catcher.catchException(e);
        }
    }

    @Override
    public void interrupt() {
        if (!isInterrupted()) {
            disconnect();
            super.interrupt();
        }
    }

    public InetAddress getInetAddress() {
        return socket.getInetAddress();
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void sendMessage(MessageModel message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

    @Override
    public void run() {
        try {
            MessageModel inputMessage, outputMessage;
            while (!isInterrupted()) {
                inputMessage = (MessageModel) inputStream.readObject();
                outputMessage = dispatch(inputMessage);
                if (outputMessage != null) {
                    this.sendMessage(outputMessage);
                }
            }

        } catch (Exception e) {
            destroyDispatcher();
            catcher.catchException(e);
        }
    }

    protected void disconnect() {
        try {
            inputStream.close();
            outputStream.close();
            if (!socket.isClosed())
                socket.close();
        } catch (IOException e) {
            catcher.catchException(e);
        }
    }

    protected abstract ResponseMessageModel dispatch(MessageModel message);

    protected abstract void destroyDispatcher();
}