package Bazhanau.Task2.Listeners;

import Bazhanau.FileService;
import Bazhanau.IFileService;
import Bazhanau.Listeners.AbstractOpenButtonListener;
import Bazhanau.Task2.MainWindow;

import java.io.File;

public class OpenButtonListener extends AbstractOpenButtonListener {
    private MainWindow _this;
    private IFileService fileService = new FileService();

    public OpenButtonListener(MainWindow _this) {
        super(_this);
        this._this = _this;
    }

    @Override
    public void Open(File file) {
    }
}