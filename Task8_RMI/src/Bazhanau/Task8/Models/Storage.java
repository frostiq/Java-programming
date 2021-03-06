package Bazhanau.Task8.Models;

import java.io.Serializable;

/**
 * @author Alex Bazhanau
 *
 * Model for storage entity
 */
public class Storage implements Serializable{
    private int id;

    private String name;

    private String location;

    public Storage() {
    }

    public Storage(int id) {
        this.id = id;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
