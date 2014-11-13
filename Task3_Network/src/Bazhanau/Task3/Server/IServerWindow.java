package Bazhanau.Task3.Server;

import Bazhanau.Logging.ICatcher;
import Bazhanau.Logging.ILogWindow;

import javax.swing.*;

public interface IServerWindow extends ILogWindow {
    JButton getStartStopButton();

    ServerBeacon getServerBeacon();

    void setServerBeacon(ServerBeacon serverBeacon);

    ICatcher getCatcher();
}
