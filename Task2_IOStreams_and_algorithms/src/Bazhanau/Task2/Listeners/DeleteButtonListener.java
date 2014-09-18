package Bazhanau.Task2.Listeners;

import Bazhanau.Task2.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteButtonListener implements ActionListener {
    private MainWindow _this;

    public DeleteButtonListener(MainWindow _this) {
        this._this = _this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _this.deleteRow(_this.getSelectedRow());
    }
}
