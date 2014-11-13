package Bazhanau.FileWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public abstract class AbstractSaveButtonListener implements ActionListener {
    private FileMainWindow _this;

    public AbstractSaveButtonListener(FileMainWindow _this) {
        this._this = _this;
    }

    public void actionPerformed(ActionEvent e) {
        if (_this.showSaveDialog() == 0) {
            _this.setIsSaved(true);
            this.save(_this.getLastSelectedFile());
        }
    }

    public abstract void save(File file);
}