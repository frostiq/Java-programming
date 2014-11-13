package Bazhanau.Task1.Listeners;

import Bazhanau.FileService.FileService;
import Bazhanau.FileService.IFileService;
import Bazhanau.FileWindow.AbstractOpenButtonListener;
import Bazhanau.Task1.MainWindow;

import java.io.File;
import java.io.IOException;

public class OpenButtonListener extends AbstractOpenButtonListener {
    private MainWindow _this;
    private IFileService fileService = new FileService();

    public OpenButtonListener(MainWindow _this) {
        super(_this);
        this._this = _this;
    }

    @Override
    public void open(File file) {
        try {
            _this.setFileText(fileService.readText(file));
        } catch (IOException e) {
            _this.catchException(e);
        }
    }
}