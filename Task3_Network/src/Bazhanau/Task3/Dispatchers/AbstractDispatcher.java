package Bazhanau.Task3.Dispatchers;

import Bazhanau.ICatcher;
import Bazhanau.Task3.Messages.MessageModel;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public abstract class AbstractDispatcher extends Thread {
    protected ICatcher catcher;
    private Socket socket;
    private JsonReader inputStream;
    private JsonWriter outputStream;
    private Gson gson;

    protected AbstractDispatcher(Socket socket, ICatcher catcher) {
        this.socket = socket;
        this.catcher = catcher;
        this.gson = new Gson();
        this.setName(this.getClass().getName());
        try {
            this.inputStream = new JsonReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = new JsonWriter(new OutputStreamWriter(socket.getOutputStream()));
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

    @Override
    public void run() {
        try {
            MessageModel inputMessage, outputMessage = null;
            while (!isInterrupted()) {
                inputMessage = readMessage();
                if (inputMessage != null) {
                    outputMessage = dispatch(inputMessage);
                } else {
                    destroyDispatcher();
                }

                if (outputMessage != null) {
                    this.sendMessage(outputMessage);
                }
            }

        } catch (Exception e) {
            destroyDispatcher();
            catcher.catchException(e);
        }
    }

    protected MessageModel readMessage() throws IOException {
        MessageModel message;
        message = gson.fromJson(inputStream, MessageModel.class);
        return message;
    }

    protected void sendMessage(MessageModel message) throws IOException {
        gson.toJson(message, MessageModel.class, outputStream);
        outputStream.flush();
    }

    protected void disconnect() {
        try {
            if (!socket.isClosed())
                socket.close();
        } catch (IOException e) {
            catcher.catchException(e);
        }
    }

    protected abstract MessageModel dispatch(MessageModel message);

    public abstract void destroyDispatcher();
}