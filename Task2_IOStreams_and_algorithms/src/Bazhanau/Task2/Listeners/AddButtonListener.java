package Bazhanau.Task2.Listeners;

import Bazhanau.Task2.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddButtonListener implements ActionListener {
    private MainWindow _this;

    public AddButtonListener(MainWindow _this) {
        this._this = _this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _this.addRow();
    }
}
