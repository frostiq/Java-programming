package Bazhanau.Task8;

import Bazhanau.Task8.Models.Item;
import Bazhanau.Task8.Models.Storage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRmiServer extends Remote, AutoCloseable {
    Item getItem(int id) throws RemoteException;

    ArrayList<Item> getItem(String name) throws RemoteException;

    ArrayList<Integer> getItemIds() throws RemoteException;

    boolean createNewItem() throws RemoteException;

    boolean createItem(Item item) throws RemoteException;

    boolean updateItem(Item item) throws RemoteException;

    boolean removeItem(int id) throws RemoteException;

    Storage getStorage(int id) throws RemoteException;

    void flush() throws RemoteException;
}
