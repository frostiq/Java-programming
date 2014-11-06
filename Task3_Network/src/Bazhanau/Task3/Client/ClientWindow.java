package Bazhanau.Task3.Client;

import Bazhanau.ICatcher;
import Bazhanau.LogCatcher;
import Bazhanau.Task3.Client.Commands.Command;
import Bazhanau.Task3.Client.Commands.ConnectToServerCommand;
import Bazhanau.Task3.Dispatchers.ClientDispatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientWindow extends JFrame implements IClientWindow {
    private ICatcher catcher = new LogCatcher(this);

    private JTextField ipField = new JTextField("127.0.0.1:");

    private JButton connectButton = new JButton("Connect to server");

    private JTextArea log = new JTextArea();

    private JTree tree = new JTree();

    private JPanel controlPanel = new JPanel(new GridLayout(0, 2));

    private ClientDispatcher clientDispatcher = null;

    private Command connectCommand = new ConnectToServerCommand(this);

    private JButton sendCommandButton = new JButton();

    private JButton sendListDirButton = new JButton();


    public ClientWindow(String title) throws HeadlessException {
        super(title);

        setLayout(new BorderLayout());
        add(new JScrollPane(tree), BorderLayout.CENTER);
        log.setPreferredSize(new Dimension(0, 50));
        add(new JScrollPane(log), BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);
        controlPanel.add(connectButton);
        controlPanel.add(ipField);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clientDispatcher == null) {
                    connectCommand.execute();
                } else {
                    connectCommand.cancel();
                }
            }
        });

        add(sendCommandButton, BorderLayout.WEST);
        sendCommandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = JOptionPane.showInputDialog("Enter command");
                if (command != null && !command.isEmpty()) {
                    try {
                        clientDispatcher.sendCommand(command);
                    } catch (Exception e1) {
                        connectCommand.cancel();
                        catcher.catchException(e1);
                    }
                }
            }
        });

        add(sendListDirButton, BorderLayout.EAST);
        sendListDirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String root = JOptionPane.showInputDialog("Enter root");
                if (root != null && !root.isEmpty()) {
                    try {
                        clientDispatcher.sendDirRequest(root);
                    } catch (Exception e1) {
                        connectCommand.cancel();
                        catcher.catchException(e1);
                    }
                }
            }
        });

        log.setEditable(false);
        setSize(400, 600);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
    public synchronized void printToLog(String message) {
        log.append(message);
        log.append("\n");
    }

    public static void main(String[] args) {
        new ClientWindow("Client");
    }
}
