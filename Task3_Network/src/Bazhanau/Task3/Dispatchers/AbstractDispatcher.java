package Bazhanau.Task3.Dispatchers;

import Bazhanau.ICatcher;
import Bazhanau.Task3.Messages.MessageModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import sun.net.ConnectionResetException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public abstract class AbstractDispatcher extends Thread implements Comparable<AbstractDispatcher> {
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

        } catch (JsonSyntaxException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof ConnectionResetException || cause instanceof SocketException)) {
                catcher.catchException(e);
                destroyDispatcher();
            }
        } catch (Exception e) {
            catcher.catchException(e);
            destroyDispatcher();
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

    @Override
    public int compareTo(AbstractDispatcher o) {
        return Integer.compare(socket.getLocalPort(), o.socket.getLocalPort());
    }
}