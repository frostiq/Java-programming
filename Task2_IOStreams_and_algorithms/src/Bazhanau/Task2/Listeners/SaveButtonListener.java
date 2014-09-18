package Bazhanau.Task2.Listeners;

import Bazhanau.FileService;
import Bazhanau.IFileService;
import Bazhanau.Listeners.AbstractSaveButtonListener;
import Bazhanau.Task2.MainWindow;

import java.io.File;

public class SaveButtonListener extends AbstractSaveButtonListener {
    private IFileService fileService = new FileService();
    private MainWindow _this;

    public SaveButtonListener(MainWindow _this) {
        super(_this);
        this._this = _this;
    }

    @Override
    public void Save(File file) {
    }
}