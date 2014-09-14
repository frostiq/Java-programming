package Bazhanau.Listeners;

import Bazhanau.FileService;
import Bazhanau.IFileService;
import Bazhanau.Task1.IMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenButtonListener
        implements ActionListener {
    private IMainWindow _this;
    private IFileService fileService = new FileService();

    public OpenButtonListener(IMainWindow _this) {
        this._this = _this;
    }

    public void actionPerformed(ActionEvent e) {
        if (_this.showOpenDialog() == 0) {
            _this.setFileText(fileService.readFile(_this.getLastSelectedFile()));
        }
    }
}