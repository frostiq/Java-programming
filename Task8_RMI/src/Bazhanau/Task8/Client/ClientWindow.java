package Bazhanau.Task8.Client;

import Bazhanau.Task8.Client.TableModels.ItemTableModel;
import Bazhanau.Task8.IRmiServer;
import Bazhanau.Task8.Models.Item;

import javax.swing.*;
import java.awt.*;

public class ClientWindow extends JFrame {

    private String[] ItemsColumnNames = new String[]{"Id", "Name", "Price", "Quantity", "Storage Id", "Storage Name"};

    private IRmiServer server = null;

    private ItemTableModel itemTableModel = new ItemTableModel(ItemsColumnNames, server);

    private JTable itemsTable = new JTable(itemTableModel);

    private JPanel controlPanel = new JPanel();

    private JButton addButton = new JButton("Add Item");

    private JButton deleteButton = new JButton("Delete Item");

    public ClientWindow() {
        setLayout(new BorderLayout());

        add(controlPanel, BorderLayout.NORTH);
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);

        add(itemsTable, BorderLayout.CENTER);

        addButton.addActionListener(e -> itemTableModel.addItem());

        deleteButton.addActionListener(e -> {
            int row = itemsTable.getSelectedRow();
            itemTableModel.deleteItem(row);
        });

        setSize(800, 400);
        setLocationByPlatform(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientWindow();
    }

}
