package Bazhanau.Task3.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerBeacon extends Thread {
    private final ArrayList<ServerDispatcher> dispatchers = new ArrayList<>();
    private ServerSocket serverSocket;
    private IServerWindow wnd;

    public ServerBeacon(IServerWindow wnd) {
        this.wnd = wnd;
        try {
            this.serverSocket = new ServerSocket(0);
            this.setName(this.getClass().getName());

        } catch (IOException e) {
            wnd.getCatcher().catchException(e);
        }
    }

    @Override
    public void run() {
        try {
            wnd.printToLog("Server is started at " + InetAddress.getLocalHost().getHostAddress() + ":" + serverSocket.getLocalPort());
            while (!isInterrupted()) {
                Socket socket = serverSocket.accept();
                wnd.printToLog(socket.getInetAddress().getHostAddress() + " connected");
                ServerDispatcher dispatcher = new ServerDispatcher(socket, this);
                dispatcher.start();
                synchronized (dispatchers) {
                    dispatchers.add(dispatcher);
                }
            }
        } catch (SocketException e) { //server is stopped manually
        } catch (IOException e) {
            wnd.printToLog(e.toString());
            this.interrupt();
        }
    }

    @Override
    public void interrupt() {
        if (!isInterrupted()) {
            synchronized (dispatchers) {
                int size = dispatchers.size();
                for (int i = size - 1; i >= 0; i--) {       //for the proper removal
                    dispatchers.get(i).destroyDispatcher();
                }
            }
        }
        try {
            serverSocket.setReuseAddress(true);
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
            wnd.printToLog("Server is stopped");
        } catch (IOException e) {
            wnd.getCatcher().catchException(e);
        } finally {
            super.interrupt();
        }
    }

    public void disconnectSocket(ServerDispatcher dispatcher) {
        dispatchers.remove(dispatcher);
        wnd.printToLog(dispatcher.getInetAddress().getHostAddress() + " disconnected");
        dispatcher.interrupt();
    }

    public IServerWindow getServerWindow() {
        return wnd;
    }
}
