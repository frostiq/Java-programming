package Bazhanau.Task9;

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
import java.util.Map;
import java.util.TreeMap;

public class XmlAdapter implements AutoCloseable {

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

    public XmlAdapter(String filePath) {
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
                    /*case XMLStreamConstants.CHARACTERS:
                        processCharacters(xmlEvent.asCharacters());
                        break;*/
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
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        input.close();
    }

    public int getNewId() {
        return newId;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public Map<Integer, Storage> getStorages() {
        return storages;
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
            if (!insideItemElement) {
                currentStorage.setName(element.getAttributeByName(nameName).getValue());
                currentStorage.setLocation(element.getAttributeByName(locationName).getValue());
            }
        }
    }

    private void processCharacters(Characters characters) {
        if(insideItemElement){
            chars.append(characters.getData());
        }
    }

    private void processEndElement(EndElement element) {
        if(element.getName().equals(itemName)) {
            int storageId = currentStorage.getId();
            currentItem.setStorage(getOrAddStorage(storageId));
            items.put(currentItem.getId(), currentItem);
            insideItemElement = false;
        }
        else if(element.getName().equals(storageName) && !insideItemElement){
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
            storages.put(storageId, res);
        }
        return res;
    }
}
