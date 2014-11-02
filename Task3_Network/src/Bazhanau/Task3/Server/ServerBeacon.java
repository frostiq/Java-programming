package Bazhanau.Task3.Server;

import Bazhanau.Task3.Dispatchers.ServerDispatcher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.TreeSet;

/*
    @TODO:
    client-side interface
    logic
    replace object streams
 */
public class ServerBeacon extends Thread {
    private ServerSocket serverSocket;
    private TreeSet<ServerDispatcher> dispatchers = new TreeSet<>();
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
            wnd.printToLog("Server is started");
            wnd.printToLog(InetAddress.getLocalHost().getHostAddress() + ":" + serverSocket.getLocalPort());
            while (!isInterrupted()) {
                Socket socket = serverSocket.accept();
                wnd.printToLog(socket.getInetAddress().getHostAddress() + " connected\n");
                ServerDispatcher dispatcher = new ServerDispatcher(socket, this);
                dispatcher.start();
                dispatchers.add(dispatcher);
            }
        } catch (SocketException e) {
        } catch (IOException e) {
            wnd.getCatcher().catchException(e);
            this.interrupt();
        }
    }

    @Override
    public void interrupt() {
        if (!isInterrupted()) {
            for (ServerDispatcher dispatcher : dispatchers) {
                this.disconnectSocket(dispatcher);
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
    }

    public void disconnectSocket(ServerDispatcher dispatcher) {
        dispatcher.interrupt();
        dispatchers.remove(dispatcher);
        wnd.printToLog(dispatcher.getInetAddress().getHostAddress() + " disconnected\n");
    }

    public IServerWindow getServerWindow() {
        return wnd;
    }
}
