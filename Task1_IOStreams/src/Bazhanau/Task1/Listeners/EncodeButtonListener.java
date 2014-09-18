package Bazhanau.Task1.Listeners;

import Bazhanau.FileService;
import Bazhanau.IFileService;
import Bazhanau.Task1.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EncodeButtonListener implements ActionListener {
    private MainWindow _this;
    private IFileService fileService = new FileService();

    public EncodeButtonListener(MainWindow _this) {
        this._this = _this;
    }

    public void actionPerformed(ActionEvent e) {
        _this.setFileText(fileService.encodeText(_this.getFileText(), _this.getKey()));
    }
}