package Bazhanau.Task3.Client.Commands;

import Bazhanau.SocketParser;
import Bazhanau.Task3.Client.ClientDispatcher;
import Bazhanau.Task3.Client.FileTreeNode;
import Bazhanau.Task3.Client.IClientWindow;

import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;
import java.net.Socket;

public class ConnectToServerCommand extends Command {
    public ConnectToServerCommand(IClientWindow wnd) {
        super(wnd);
    }

    @Override
    public synchronized void execute() throws IOException {
        SocketParser socketParser = new SocketParser(wnd.getCatcher());
        Socket socket = socketParser.parse(wnd.getIpField().getText());
        wnd.setClientDispatcher(new ClientDispatcher(socket, wnd.getCatcher(), wnd));

        if (wnd.getClientDispatcher().isConnected()) {
            wnd.printToLog("Connected successfully to " + wnd.getClientDispatcher().getInetAddress());
            wnd.getConnectButton().setText("Disconnect");
            wnd.getClientDispatcher().start();
            wnd.setTreeModel(new DefaultTreeModel(new FileTreeNode(wnd.getClientDispatcher(), "C:\\"), true));
        } else {
            wnd.printToLog("Connection failed!");
            this.cancel();
        }
    }

    @Override
    public synchronized void cancel() {
        if (wnd.getClientDispatcher() != null) {
            wnd.getConnectButton().setText("Connect to server");
            wnd.printToLog("Disconnected from server");
            if (!wnd.getClientDispatcher().isInterrupted()) {
                wnd.getClientDispatcher().interrupt();
            }
            wnd.setTreeModel(new DefaultTreeModel(null, true));
            wnd.setClientDispatcher(null);
        }
    }
}
