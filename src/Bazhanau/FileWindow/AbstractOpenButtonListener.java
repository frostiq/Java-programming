package Bazhanau.FileWindow;

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
            this.open(_this.getLastSelectedFile());
            _this.setIsSaved(true);
        }
    }

    public abstract void open(File file);
}