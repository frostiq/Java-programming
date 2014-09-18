package Bazhanau.Task1.Listeners;

import Bazhanau.Catcher;
import Bazhanau.FileService;
import Bazhanau.IFileService;
import Bazhanau.Listeners.AbstractOpenButtonListener;
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
            Catcher.catchException(e);
        }
    }
}