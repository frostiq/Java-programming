package Bazhanau.Task3.Client;

import Bazhanau.ICatcher;
import Bazhanau.LogCatcher;
import Bazhanau.Task3.Client.Commands.Command;
import Bazhanau.Task3.Client.Commands.ConnectToServerCommand;
import Bazhanau.Task3.Dispatchers.ClientDispatcher;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ClientWindow extends JFrame implements IClientWindow {
    private ICatcher catcher = new LogCatcher(this);

    private JTextField ipField = new JTextField("localhost:");

    private JButton connectButton = new JButton("Connect to server");

    private JTree tree = new JTree(new DefaultTreeModel(null, true));

    private JTextArea log = new JTextArea();

    private JPanel controlPanel = new JPanel(new GridLayout(0, 2));

    private ClientDispatcher clientDispatcher = null;

    private Command connectCommand = new ConnectToServerCommand(this);


    public ClientWindow(String title) throws HeadlessException {
        super(title);

        setLayout(new BorderLayout());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        add(new JScrollPane(tree), BorderLayout.CENTER);
        log.setEditable(false);
        add(new JScrollPane(log), BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);
        controlPanel.add(connectButton);
        controlPanel.add(ipField);

        connectButton.addActionListener((e) -> handleConnectionAction());

        ipField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    handleConnectionAction();
                }
            }
        });

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    try {
                        FileTreeNode file = (FileTreeNode) tree.getSelectionPath().getLastPathComponent();
                        if (!file.getAllowsChildren()) {
                            clientDispatcher.sendExecRequest(file.getCurrentPath());
                        }
                    } catch (IOException e1) {
                        catcher.catchException(e1);
                    }
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
                clientDispatcher.destroyDispatcher();
            }
        } catch (Exception e1) {
            catcher.catchException(e1);
        }
    }
}
