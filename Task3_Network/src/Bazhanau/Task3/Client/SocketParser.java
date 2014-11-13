package Bazhanau.Task3.Client;

import Bazhanau.Logging.ICatcher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketParser {
    private ICatcher catcher;

    public SocketParser(ICatcher catcher) {
        this.catcher = catcher;
    }

    public Socket parse(String text) throws IOException {
        String[] arr = text.split(":");
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getByName(arr[0]), new Integer(arr[1])), 1000);
        return socket;
    }
}
