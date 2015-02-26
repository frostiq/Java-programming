package Bazhanau.Task8.Server;

import Bazhanau.Task7.MSSQLManager;
import Bazhanau.Task8.Models.Item;
import Bazhanau.Task8.Models.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlAdapter extends RmiServer{
    public static final String INSERT_ITEM = "INSERT INTO Items(name, price, quantity, storage_id) VALUES(?,?,?,?)";
    public static final String UPDATE_ITEM = "UPDATE Items SET name = ?, price = ?, quantity = ?, storage_id = ? WHERE id = ?";
    public static final String DELETE_ITEM = "DELETE FROM Items WHERE id = ?";
    public static final String SELECT_ITEM = "SELECT * FROM Items WHERE id = ?";
    public static final String SELECT_ITEM_BY_NAME = "SELECT * FROM Items WHERE name = ?";
    public static final String INSERT_STORAGE = "INSERT INTO Storages(id, name, location) VALUES(?,?,?)";
    public static final String UPDATE_STORAGE = "UPDATE Storages SET name = ?, location = ? WHERE id = ?";
    public static final String DELETE_STORAGE = "DELETE FROM Storages WHERE id = ?";
    public static final String SELECT_STORAGE = "SELECT * FROM Storages WHERE id = ?";
    Connection connection;

    public SqlAdapter() {
        connection = new MSSQLManager("JavaTask8").getConnection();
    }

    @Override
    public Item getItem(int id) {
        Item item = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ITEM);
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
    public boolean createItem(Item item) {
        try {
            PreparedStatement res = connection.prepareStatement(INSERT_ITEM);
            res.setString(1, item.getName());
            res.setInt(2, item.getPrice());
            res.setInt(3, item.getQuantity());
            res.setInt(4, item.getStorage().getId());
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
            res.setInt(4, item.getStorage().getId());
            res.setInt(5, item.getId());
            res.executeUpdate();
        } catch (Exception e) {
            catcher.catchException(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean removeItem(Item item) {
        try {
            PreparedStatement res = connection.prepareStatement(DELETE_ITEM);
            res.setInt(1, item.getId());
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

    public static void main(String[] args) {
        new SqlAdapter();
    }
}
