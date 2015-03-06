package Bazhanau.Task8.Client;

import Bazhanau.Logging.ICatcher;
import Bazhanau.Logging.MessageBoxCatcher;
import Bazhanau.Task8.Client.TableModels.ItemTableModel;
import Bazhanau.Task8.IRmiServer;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientWindow extends JFrame {

    private String[] ItemsColumnNames = new String[]{"Id", "Name", "Price", "Quantity", "Storage Id", "Storage Name"};

    private IRmiServer server;

    private ItemTableModel itemsTableModel;

    private JTable itemsTable;

    private JPanel controlPanel = new JPanel();

    private JButton addButton = new JButton("Add Item");

    private JButton deleteButton = new JButton("Delete Item");

    private JButton updateButton = new JButton("Update Table");

    private JButton findButton = new JButton("Find by name");

    private ICatcher catcher = new MessageBoxCatcher(this);


    public ClientWindow() {
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.137.247", 16666);
            server = (IRmiServer)registry.lookup("Server");
            itemsTableModel = new ItemTableModel(ItemsColumnNames, server, catcher);
            itemsTable = new JTable(itemsTableModel);
        } catch (Exception e) {
            catcher.catchException(e);
        }


        setLayout(new BorderLayout());

        add(controlPanel, BorderLayout.NORTH);
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        controlPanel.add(updateButton);
        controlPanel.add(findButton);

        add(new JScrollPane(itemsTable), BorderLayout.CENTER);

        addButton.addActionListener(e -> itemsTableModel.addItem());

        deleteButton.addActionListener(e -> {
            int row = itemsTable.getSelectedRow();
            itemsTableModel.deleteItem(row);
        });

        findButton.addActionListener(e -> {
            String res = JOptionPane.showInputDialog(this, "Search by name");
            if (res != null)
                itemsTableModel.findByName(res);
        });

        updateButton.addActionListener(e -> itemsTableModel.fireTableDataChanged());

        setSize(800, 400);
        setLocationByPlatform(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientWindow();
    }

}
