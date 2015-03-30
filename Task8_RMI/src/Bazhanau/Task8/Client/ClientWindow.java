package Bazhanau.Task8.Client;

import Bazhanau.Logging.ICatcher;
import Bazhanau.Logging.MessageBoxCatcher;
import Bazhanau.Task5.ResourceManager;
import Bazhanau.Task8.Client.TableModels.ItemTableModel;
import Bazhanau.Task8.IRmiServer;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Locale;

public class ClientWindow extends JFrame {
    private ResourceManager resourceManager = ResourceManager.INSTANCE;

    private String[] ItemsColumnNames = new String[]{
            resourceManager.getString("id"),
            resourceManager.getString("name"),
            resourceManager.getString("price"),
            resourceManager.getString("quantity"),
            resourceManager.getString("storage_id"),
            resourceManager.getString("storage_name")};

    private IRmiServer server;

    private ItemTableModel itemsTableModel;

    private JTable itemsTable;

    private JPanel controlPanel = new JPanel();

    private JButton addButton = new JButton(resourceManager.getString("add_item"));

    private JButton deleteButton = new JButton(resourceManager.getString("delete_item"));

    private JButton updateButton = new JButton(resourceManager.getString("update_table"));

    private JButton findButton = new JButton(resourceManager.getString("find_by_name"));

    private JButton flushButton = new JButton(resourceManager.getString("flush_server_cache"));

    private JMenuBar menuBar = new JMenuBar();

    private JMenu menu = new JMenu(ResourceManager.INSTANCE.getString("language"));

    private ArrayList<JMenuItem> languageMenuItems = new ArrayList<>();

    private ICatcher catcher = new MessageBoxCatcher(this);


    public ClientWindow() {
        try {
            String address = JOptionPane.showInputDialog(resourceManager.getString("type_registry_address"), "localhost:16666");
            if (address == null) return;

            String[] temp = address.split(":");
            Registry registry = LocateRegistry.getRegistry(temp[0], Integer.parseInt(temp[1]));
            server = (IRmiServer) registry.lookup("Server");
            itemsTableModel = new ItemTableModel(ItemsColumnNames, server, catcher);
            itemsTable = new JTable(itemsTableModel);
        } catch (Exception e) {
            catcher.catchException(e);
        }

        initLanguageMenu();
        setLayout(new BorderLayout());

        add(controlPanel, BorderLayout.NORTH);
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        controlPanel.add(updateButton);
        controlPanel.add(findButton);
        controlPanel.add(flushButton);

        add(new JScrollPane(itemsTable), BorderLayout.CENTER);

        addButton.addActionListener(e -> itemsTableModel.addItem());

        deleteButton.addActionListener(e -> {
            int row = itemsTable.getSelectedRow();
            itemsTableModel.deleteItem(row);
        });

        findButton.addActionListener(e -> {
            String res = JOptionPane.showInputDialog(this, resourceManager.getString("find_by_name"));
            if (res != null)
                itemsTableModel.findByName(res);
        });

        updateButton.addActionListener(e -> itemsTableModel.fireTableDataChanged());

        flushButton.addActionListener(e -> {
            try {
                server.flush();
            } catch (RemoteException ex) {
                catcher.catchException(ex);
            }
        });

        setSize(800, 400);
        setLocationByPlatform(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientWindow();
    }

    private void initLanguageMenu() {
        setJMenuBar(menuBar);
        menuBar.add(menu);
        languageMenuItems.add(new JMenuItem("en"));
        languageMenuItems.add(new JMenuItem("be"));
        for (JMenuItem jMenuItem : languageMenuItems) {
            jMenuItem.addActionListener(e -> {
                ResourceManager.INSTANCE.changeLocale(new Locale(jMenuItem.getText()));
                JOptionPane.showMessageDialog(
                        ClientWindow.this,
                        ResourceManager.INSTANCE.getString("reload.app"),
                        "",
                        JOptionPane.WARNING_MESSAGE);
            });
            menu.add(jMenuItem);
        }
    }

}
