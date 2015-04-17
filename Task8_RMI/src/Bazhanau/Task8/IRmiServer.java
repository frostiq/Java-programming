package Bazhanau.Task8;

import Bazhanau.Task8.Models.Item;
import Bazhanau.Task8.Models.Storage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author Alex Bazhanau
 *
 * interface for remote server for managing
 * <ul>
 *     <li>items</li>
 *     <li>storages</li>
 * </ul>
 * communication maintais by Remote Method Invocation
 * @see <a href="http://habrahabr.ru/post/74639/">RMI</a>
 */
public interface IRmiServer extends Remote, AutoCloseable {
    /**
     *
     * @param id of the item
     * @return Item with specified id from DB
     *
     * @see Bazhanau.Task8.Models.Item
     * @throws java.rmi.RemoteException
     */
    Item getItem(int id) throws RemoteException;

    /**
     *
     * @param name of the items
     * @return Items with specified name from DB
     *
     * @see Bazhanau.Task8.Models.Item
     * @throws java.rmi.RemoteException
     */
    ArrayList<Item> getItem(String name) throws RemoteException;

    /**
     * @return Ids of all of Items in DB
     * @throws java.rmi.RemoteException
     */
    ArrayList<Integer> getItemIds() throws RemoteException;

    /**
     * Creates new item with default values in DB
     * @return true if creation succeeded, otherwise false
     * @throws java.rmi.RemoteException
     */
    boolean createNewItem() throws RemoteException;

    /**
     * Creates new item with values in DB, copied from <code>item</code>
     * @param item source of values
     * @return true if creation succeeded, otherwise false
     * @throws java.rmi.RemoteException
     */
    boolean createItem(Item item) throws RemoteException;

    /**
     * Updates item in DB with values from <code>item</code>
     * @param item source of values
     * @return true if update succeeded, otherwise false
     * @throws java.rmi.RemoteException
     */
    boolean updateItem(Item item) throws RemoteException;

    /**
     * Removes item from DB with specified <code>id</code>
     * @see java.lang.Integer
     * @return true if delete succeeded, otherwise false
     * @throws java.rmi.RemoteException
     */
    boolean removeItem(int id) throws RemoteException;

    /**
     * @return Storage with specified <code>id</code> from DB
     * @see java.lang.Integer
     * @throws java.rmi.RemoteException
     */
    Storage getStorage(int id) throws RemoteException;

    /**
     * flushes server cache
     * @throws RemoteException
     */
    void flush() throws RemoteException;
}
