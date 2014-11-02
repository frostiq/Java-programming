package Bazhanau.Task3.Client.Commands;

import Bazhanau.Task3.Client.IClientWindow;
import Bazhanau.Task3.Client.SocketParser;
import Bazhanau.Task3.Dispatchers.ClientDispatcher;

import java.net.Socket;

public class ConnectToServerCommand extends Command {
    public ConnectToServerCommand(IClientWindow wnd) {
        super(wnd);
    }

    @Override
    public void execute() {
        SocketParser socketParser = new SocketParser(wnd.getCatcher());
        Socket socket = socketParser.parse(wnd.getIpField().getText());
        wnd.setClientDispatcher(new ClientDispatcher(socket, wnd.getCatcher()));

        if (wnd.getClientDispatcher().isConnected()) {
            wnd.printToLog("Connected successfully to " + wnd.getClientDispatcher().getInetAddress());
            wnd.getConectButton().setText("Disconnect");
            wnd.getClientDispatcher().start();
        } else
            wnd.printToLog("Connection failed!");
        this.cancel();
    }

    @Override
    public void cancel() {
        wnd.getConectButton().setText("Connect to server");
        if (!wnd.getClientDispatcher().isInterrupted()) {
            wnd.getClientDispatcher().interrupt();
        }
        wnd.setClientDispatcher(null);
    }
}
