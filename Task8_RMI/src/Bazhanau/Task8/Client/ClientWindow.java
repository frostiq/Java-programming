package Bazhanau.Task8.Client;

import javax.swing.*;

public class ClientWindow extends JFrame {

    protected final String[] ItemsColumnNames = new String[] {"Id", "Name", "Location"};
    private JPanel controlPanel = new JPanel();
    private String[] groupsColumnNames = new String[]{"Id", "Name", "Price", "Quantity", "Storage Id"};
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JButton addButton = new JButton("Дадаць студэнта");
    private JButton deleteButton = new JButton("Выдаліць абранага студэнта");

    public static void main(String[] args) {
        new ClientWindow();
    }

}
