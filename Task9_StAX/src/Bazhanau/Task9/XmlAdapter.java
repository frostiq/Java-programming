package Bazhanau.Task9;

import Bazhanau.Logging.ConsoleCatcher;
import Bazhanau.Logging.ICatcher;
import Bazhanau.Task8.IRmiServer;
import Bazhanau.Task8.Models.Item;
import Bazhanau.Task8.Models.Storage;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XmlAdapter implements IRmiServer {
    private ICatcher catcher;

    private FileInputStream input;
    private XMLInputFactory factory;
    private XMLEventReader reader;

    private QName itemName = new QName("item");
    private QName storageName = new QName("storage");
    private QName idName = new QName("id");
    private QName nameName = new QName("name");
    private QName priceName = new QName("price");
    private QName quantityName = new QName("quantity");
    private QName locationName = new QName("location");

    private boolean insideItemElement;

    private StringBuilder chars;
    private Item currentItem;
    private Storage currentStorage;

    private Map<Integer, Item> items;
    private Map<Integer, Storage> storages;

    private int newId = 1;

    public XmlAdapter(String filePath, ICatcher catcher) {
        this.catcher = catcher;

        try {
            input = new FileInputStream(filePath);
            factory = XMLInputFactory.newInstance();
            reader = factory.createXMLEventReader(input);

            for (XMLEvent xmlEvent  : ((Iterable<XMLEvent>) () -> reader)){
                switch(xmlEvent.getEventType())
                {
                    case XMLStreamConstants.START_DOCUMENT:
                        init();
                        break;
                    case XMLStreamConstants.START_ELEMENT:
                        processStartElement(xmlEvent.asStartElement());
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        processCharacters(xmlEvent.asCharacters());
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        processEndElement(xmlEvent.asEndElement());
                        break;
                    case XMLStreamConstants.END_DOCUMENT:
                        cleanup();
                        break;
                }
            }
        }
        catch (Exception e){
            this.catcher.catchException(e);
        }

    }

    public static void main(String[] args) {
        new XmlAdapter("C:\\Users\\Аляксандр\\IdeaProjects\\Java-programming\\Task9_StAX/data.xml", new ConsoleCatcher());
    }

    private void init() {
        items = new TreeMap<>();
        storages = new TreeMap<>();
        chars = new StringBuilder();
        insideItemElement = false;
    }

    private void processStartElement(StartElement element){
        if(element.getName().equals(itemName))
        {
            currentItem = new Item();
            currentItem.setId(Integer.parseInt(element.getAttributeByName(idName).getValue()));
            currentItem.setName(element.getAttributeByName(nameName).getValue());
            currentItem.setPrice(Integer.parseInt(element.getAttributeByName(priceName).getValue()));
            currentItem.setQuantity(Integer.parseInt(element.getAttributeByName(quantityName).getValue()));
            insideItemElement = true;

            newId = Math.max(newId, currentItem.getId() + 1);
        }
        else if(element.getName().equals(storageName)){
            currentStorage = new Storage();
            currentStorage.setId(Integer.parseInt(element.getAttributeByName(idName).getValue()));
            currentStorage.setName(element.getAttributeByName(nameName).getValue());
            currentStorage.setLocation(element.getAttributeByName(locationName).getValue());
        }
    }

    private void processCharacters(Characters characters) {
        if(insideItemElement){
            chars.append(characters.getData());
        }
    }

    private void processEndElement(EndElement element) {
        if(element.getName().equals(itemName)) {
            int storageId = Integer.parseInt(chars.toString());
            currentItem.setStorage(getOrAddStorage(storageId));
            items.put(currentItem.getId(), currentItem);
        }
        else if(element.getName().equals(storageName)){
            storages.put(currentStorage.getId(), currentStorage);
        }
    }

    private void cleanup() {
        insideItemElement = false;
        currentItem = null;
        currentStorage = null;
    }

    private Storage getOrAddStorage(int storageId) {
        Storage res = storages.get(storageId);

        if(res == null){
            res = new Storage(storageId);
            res = storages.put(storageId, res);
        }
        return res;
    }

    @Override
    public Item getItem(int id) throws RemoteException {
        return items.get(id);
    }

    @Override
    public ArrayList<Item> getItem(String name) throws RemoteException {
        Stream<Item> stream = items.values().stream().filter(i -> i.getName().equals(name));
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
        input.close();
    }
}
