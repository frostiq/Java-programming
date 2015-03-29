package Bazhanau.Task8.Server;

import Bazhanau.Task7.MSSQLManager;
import Bazhanau.Task8.Models.Item;
import Bazhanau.Task8.Models.Storage;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;

public class SqlAdapter extends RmiServer{
    public static final String INSERT_ITEM = "INSERT INTO Items(name, price, quantity, storage_id) VALUES(?,?,?,?)";
    public static final String UPDATE_ITEM = "UPDATE Items SET name = ?, price = ?, quantity = ?, storage_id = ? WHERE id = ?";
    public static final String DELETE_ITEM = "DELETE FROM Items WHERE id = ?";
    public static final String SELECT_ITEM = "SELECT * FROM Items WHERE id = ?";
    public static final String SELECT_ITEM_BY_NAME = "SELECT * FROM Items WHERE name = ?";
    public static final String SELECT_ITEM_IDS = "SELECT id FROM Items";
    public static final String INSERT_STORAGE = "INSERT INTO Storages(id, name, location) VALUES(?,?,?)";
    public static final String UPDATE_STORAGE = "UPDATE Storages SET name = ?, location = ? WHERE id = ?";
    public static final String DELETE_STORAGE = "DELETE FROM Storages WHERE id = ?";
    public static final String SELECT_STORAGE = "SELECT * FROM Storages WHERE id = ?";
    Connection connection;

    public SqlAdapter() {
        connection = new MSSQLManager("JavaTask8").getConnection();
    }

    public static void main(String[] args) {
        new SqlAdapter();
    }

    @Override
    public Item getItem(int id) {
        Item item = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ITEM);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                item = buildItem(res);
            }

        } catch (Exception e) {
            catcher.catchException(e);
        }
        return item;
    }

    @Override
    public ArrayList<Item> getItem(String name) {
        ArrayList<Item> items = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ITEM_BY_NAME);
            statement.setString(1, name);
            ResultSet res = statement.executeQuery();

            while (res.next()){
                Item item = buildItem(res);
                items.add(item);
            }
        } catch (Exception e) {
            catcher.catchException(e);
        }
        return items;
    }

    @Override
    public ArrayList<Integer> getItemIds() {
        ArrayList<Integer> items = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ITEM_IDS);
            ResultSet res = statement.executeQuery();

            while (res.next()){
                int item = res.getInt("Id");
                items.add(item);
            }
        } catch (Exception e) {
            catcher.catchException(e);
        }
        return items;
    }

    @Override
    public boolean createNewItem(){
        Item item = new Item();
        item.setName("new item");
        item.setPrice(0);
        item.setQuantity(0);
        return createItem(item);
    }

    @Override
    public boolean createItem(Item item) {
        try {
            PreparedStatement res = connection.prepareStatement(INSERT_ITEM);
            res.setString(1, item.getName());
            res.setInt(2, item.getPrice());
            res.setInt(3, item.getQuantity());
            if(item.getStorage() != null)
                res.setInt(4, item.getStorage().getId());
            else
                res.setNull(4, Types.INTEGER);
            res.executeUpdate();
        } catch (Exception e) {
            catcher.catchException(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            PreparedStatement res = connection.prepareStatement(UPDATE_ITEM);
            res.setString(1, item.getName());
            res.setInt(2, item.getPrice());
            res.setInt(3, item.getQuantity());
            if(item.getStorage() != null)
                res.setInt(4, item.getStorage().getId());
            else
                res.setNull(4, Types.INTEGER);
            res.setInt(5, item.getId());
            res.executeUpdate();
        } catch (Exception e) {
            catcher.catchException(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean removeItem(int id) {
        try {
            PreparedStatement res = connection.prepareStatement(DELETE_ITEM);
            res.setInt(1, id);
            res.executeUpdate();
        } catch (Exception e) {
            catcher.catchException(e);
            return false;
        }
        return true;
    }

    @Override
    public Storage getStorage(int id) {
        Storage storage = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_STORAGE);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                storage = buildStorage(res);
            }
        } catch (Exception e) {
            catcher.catchException(e);
        }
        return storage;
    }

    @Override
    public void flush() throws RemoteException {

    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    protected Item buildItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setPrice(resultSet.getInt("price"));
        item.setQuantity(resultSet.getInt("quantity"));
        item.setStorage(this.getStorage(resultSet.getInt("storage_id")));
        return item;
    }

    protected Storage buildStorage(ResultSet resultSet) throws SQLException{
        Storage storage = new Storage();
        storage.setId(resultSet.getInt("id"));
        storage.setName(resultSet.getString("name"));
        storage.setLocation(resultSet.getString("location"));
        return storage;
    }
}
