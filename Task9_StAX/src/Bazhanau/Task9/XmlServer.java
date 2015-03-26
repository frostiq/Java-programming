package Bazhanau.Task9;

import Bazhanau.Task8.Models.Item;
import Bazhanau.Task8.Models.Storage;
import Bazhanau.Task8.Server.RmiServer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*TODO:
    -data write
    -localization
 */

public class XmlServer extends RmiServer {
    private Map<Integer, Item> items;

    private Map<Integer, Storage> storages;

    private int newId;

    private XmlAdapter adapter;

    public XmlServer(String storagePath, String schemaPath) {
        adapter = new XmlAdapter(storagePath, schemaPath);
        this.items = adapter.getItems();
        this.storages = adapter.getStorages();
        this.newId = adapter.getNewId();
    }

    public static void main(String[] args) {
        new XmlServer("Task9_StAX/data.xml", "Task9_StAX/data.xsd");
    }


    @Override
    public Item getItem(int id) throws RemoteException {
        Item res = items.get(id);
        return fillItem(res);
    }

    @Override
    public ArrayList<Item> getItem(String name) throws RemoteException {
        Stream<Item> stream = items.values().stream().filter(i -> i.getName().equals(name))
                                                     .map(i -> fillItem(i));
        return new ArrayList<>(stream.collect(Collectors.toList()));
    }

    @Override
    public ArrayList<Integer> getItemIds() throws RemoteException {
        return new ArrayList<>(items.keySet());
    }

    @Override
    public boolean createNewItem() throws RemoteException {
        Item item = new Item();
        item.setId(newId++);
        item.setName("new item");
        item.setPrice(0);
        item.setQuantity(0);
        items.put(item.getId(), item);

        return true;
    }

    @Override
    public boolean createItem(Item item) throws RemoteException {
        item.setId(newId++);
        items.put(item.getId(), item);

        return true;
    }

    @Override
    public boolean updateItem(Item item) throws RemoteException {
        Item prev = items.replace(item.getId(), item);
        return prev != null;
    }

    @Override
    public boolean removeItem(int id) throws RemoteException {
        Item prev = items.remove(id);
        return prev != null;
    }

    @Override
    public Storage getStorage(int id) throws RemoteException {
        return storages.get(id);
    }

    @Override
    public void close() throws Exception {
    }

    private Item fillItem(Item item){
        Storage storage = item.getStorage();
        if(storage != null){
            item.setStorage(storages.get(storage.getId()));
        }
        return item;
    }
}
