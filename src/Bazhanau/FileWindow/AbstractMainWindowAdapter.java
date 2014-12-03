package Bazhanau.FileWindow;

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
        if (_this.hasNoInfoToSave() || _this.isSaved()) {
            System.exit(0);
        }
        int res = JOptionPane.showConfirmDialog(_this, _this.getSaveQuestion(), "", JOptionPane.YES_NO_CANCEL_OPTION);
        if (res == JOptionPane.CANCEL_OPTION) {
            return;
        }
        if (res == JOptionPane.YES_OPTION) {
            if (_this.showSaveDialog() == JFileChooser.APPROVE_OPTION) {
                _this.setIsSaved(true);
                this.save(_this.getLastSelectedFile());
            } else {
                return;
            }
        }
        System.exit(0);
    }

    public abstract void save(File file);
}