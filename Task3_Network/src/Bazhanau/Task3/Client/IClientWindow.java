package Bazhanau.Task3.Client;

import Bazhanau.ICatcher;
import Bazhanau.ILogWindow;
import Bazhanau.Task3.Dispatchers.ClientDispatcher;

import javax.swing.*;
import javax.swing.tree.TreeModel;

public interface IClientWindow extends ILogWindow {
    ClientDispatcher getClientDispatcher();

    void setClientDispatcher(ClientDispatcher clientDispatcher);

    ICatcher getCatcher();

    JTextField getIpField();

    JButton getConnectButton();

    void setTreeModel(TreeModel treeModel);

    void closeConnection();
}
