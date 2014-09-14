package Bazhanau.Listeners;

import Bazhanau.FileService;
import Bazhanau.IFileService;
import Bazhanau.Task1.IMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButtonListener implements ActionListener {
    private IMainWindow _this;
    private IFileService fileService = new FileService();

    public SaveButtonListener(IMainWindow _this) {
        this._this = _this;
    }

    public void actionPerformed(ActionEvent e) {
        if (_this.showSaveDialog() == 0) {
            fileService.writeText(_this.getLastSelectedFile(), _this.getFileText());
        }
    }
}