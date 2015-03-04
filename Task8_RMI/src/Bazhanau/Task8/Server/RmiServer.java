package Bazhanau.Task8.Server;

import Bazhanau.Logging.ICatcher;
import Bazhanau.Logging.MessageBoxCatcher;
import Bazhanau.Task8.IRmiServer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public abstract class RmiServer implements IRmiServer{

    protected ICatcher catcher = new MessageBoxCatcher(null);

    public RmiServer() {
        try {
            IRmiServer server = (IRmiServer) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.createRegistry(16666);
            registry.bind("Server", server);
        } catch (RemoteException | AlreadyBoundException e) {
            catcher.catchException(e);
        }
    }
}
