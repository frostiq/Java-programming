package Bazhanau.Listeners;

import Bazhanau.FileMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public abstract class AbstractOpenButtonListener implements ActionListener {
    private FileMainWindow _this;

    public AbstractOpenButtonListener(FileMainWindow _this) {
        this._this = _this;
    }

    public void actionPerformed(ActionEvent e) {
        if (_this.showOpenDialog() == 0) {
            this.Open(_this.getLastSelectedFile());
        }
    }

    public abstract void Open(File file);
}