package Bazhanau.Task8;

import Bazhanau.Task8.Models.Item;
import Bazhanau.Task8.Models.Storage;

import java.rmi.Remote;
import java.util.ArrayList;

public interface IRmiServer extends Remote, AutoCloseable {
    Item getItem(int id);

    ArrayList<Item> getItem(String name);

    ArrayList<Integer> getItemIds();

    boolean createNewItem();

    boolean createItem(Item item);

    boolean updateItem(Item item);

    boolean removeItem(int id);

    Storage getStorage(int id);
}
