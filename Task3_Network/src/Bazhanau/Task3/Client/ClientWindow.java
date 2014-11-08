package Bazhanau.Task3.Client;

import Bazhanau.ICatcher;
import Bazhanau.LogCatcher;
import Bazhanau.Task3.Client.Commands.Command;
import Bazhanau.Task3.Client.Commands.ConnectToServerCommand;
import Bazhanau.Task3.Dispatchers.ClientDispatcher;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientWindow extends JFrame implements IClientWindow {
    private ICatcher catcher = new LogCatcher(this);

    private JTextField ipField = new JTextField("192.168.1.104:");

    private JButton connectButton = new JButton("Connect to server");

    private JTree tree = new JTree(new DefaultTreeModel(null, true));

    private JTextArea log = new JTextArea();

    private JPanel controlPanel = new JPanel(new GridLayout(0, 2));

    private ClientDispatcher clientDispatcher = null;

    private Command connectCommand = new ConnectToServerCommand(this);

    private JButton sendCommandButton = new JButton();

    private JButton sendListDirButton = new JButton();


    public ClientWindow(String title) throws HeadlessException {
        super(title);

        setLayout(new BorderLayout());
        add(new JScrollPane(tree), BorderLayout.CENTER);
        log.setEditable(false);
        add(new JScrollPane(log), BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);
        controlPanel.add(connectButton);
        controlPanel.add(ipField);

        connectButton.addActionListener((e) -> handleConnectionAction());

        add(sendCommandButton, BorderLayout.WEST);
        sendCommandButton.addActionListener((e) -> {
                String command = JOptionPane.showInputDialog("Enter command");
                if (command != null && !command.isEmpty()) {
                    try {
                        clientDispatcher.sendExecRequest(command);
                    } catch (Exception e1) {
                        connectCommand.cancel();
                        catcher.catchException(e1);
                    }
                }
        });

        add(sendListDirButton, BorderLayout.EAST);
        sendListDirButton.addActionListener((e) -> {
                String root = JOptionPane.showInputDialog("Enter root");
                if (root != null && !root.isEmpty()) {
                    try {
                        clientDispatcher.sendDirRequest(root);
                    } catch (Exception e1) {
                        connectCommand.cancel();
                        catcher.catchException(e1);
                    }
                }
        });

        ipField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    handleConnectionAction();
                }
            }
        });

        setSize(400, 600);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new ClientWindow("Client");
    }

    @Override
    public ClientDispatcher getClientDispatcher() {
        return clientDispatcher;
    }

    @Override
    public void setClientDispatcher(ClientDispatcher clientDispatcher) {
        this.clientDispatcher = clientDispatcher;
    }

    @Override
    public ICatcher getCatcher() {
        return catcher;
    }

    @Override
    public JTextField getIpField() {
        return ipField;
    }

    @Override
    public JButton getConnectButton() {
        return connectButton;
    }

    @Override
    public void setTreeModel(TreeModel treeModel) {
        tree.setModel(treeModel);
    }

    @Override
    public synchronized void printToLog(String message) {
        log.append(message);
        log.append("\n");
    }

    @Override
    public void closeConnection() {
        connectCommand.cancel();
    }

    private void handleConnectionAction() {
        try {
            if (clientDispatcher == null) {
                connectCommand.execute();
            } else {
                connectCommand.cancel();
            }
        } catch (Exception e1) {
            catcher.catchException(e1);
        }
    }
}
