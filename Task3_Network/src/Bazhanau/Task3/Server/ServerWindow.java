package Bazhanau.Task3.Server;

import Bazhanau.Logging.ICatcher;
import Bazhanau.Logging.LogCatcher;
import Bazhanau.Task3.Server.Commands.Command;
import Bazhanau.Task3.Server.Commands.StartServerCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame implements IServerWindow {
    private ICatcher catcher = new LogCatcher(this);

    private JTextArea log = new JTextArea();

    private JButton startStopButton = new JButton("Start server");

    private ServerBeacon serverBeacon;

    private Command startCommand = new StartServerCommand(this);

    public ServerWindow(String title) throws HeadlessException {
        super(title);

        setLayout(new BorderLayout());
        add(new JScrollPane(log), BorderLayout.CENTER);
        add(startStopButton, BorderLayout.NORTH);

        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverBeacon == null) {
                    startCommand.execute();
                } else {
                    startCommand.cancel();
                }
            }
        });

        log.setEditable(false);
        setSize(400, 600);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new ServerWindow("Server");
    }

    @Override
    public synchronized void printToLog(String message) {
        log.append(message);
        log.append("\n");
    }

    @Override
    public JButton getStartStopButton() {
        return startStopButton;
    }

    @Override
    public ServerBeacon getServerBeacon() {
        return serverBeacon;
    }

    @Override
    public void setServerBeacon(ServerBeacon serverBeacon) {
        this.serverBeacon = serverBeacon;
    }

    @Override
    public ICatcher getCatcher() {
        return catcher;
    }
}
