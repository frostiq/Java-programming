package Bazhanau.Listeners;

import Bazhanau.FileMainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public abstract class AbstractMainWindowAdapter extends WindowAdapter {
    private FileMainWindow _this;

    public AbstractMainWindowAdapter(FileMainWindow _this) {
        this._this = _this;
    }

    public void windowClosing(WindowEvent e) {
        if (_this.hasNoInfoToSave()) {
            System.exit(0);
        }
        int res = JOptionPane.showConfirmDialog(_this, _this.getSaveQuestion(), "", JOptionPane.YES_NO_CANCEL_OPTION);
        if (res == JOptionPane.CANCEL_OPTION) {
            return;
        }
        if (res == JOptionPane.YES_OPTION) {
            if (_this.showSaveDialog() == JFileChooser.APPROVE_OPTION) {
                this.Save(_this.getLastSelectedFile());
            } else {
                return;
            }
        }
        System.exit(0);
    }

    public abstract void Save(File file);
}