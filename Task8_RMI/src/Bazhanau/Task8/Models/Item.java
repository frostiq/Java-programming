package Bazhanau.Task8.Models;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;

    private String name;

    private int price;

    private int quantity;

    private Storage storage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Storage getStorage() {
        return storage;
    }

    public Item setStorage(Storage storage) {
        this.storage = storage;
        return this;
    }
}
