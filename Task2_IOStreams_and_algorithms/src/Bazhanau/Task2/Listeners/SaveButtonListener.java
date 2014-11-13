package Bazhanau.Task2.Listeners;

import Bazhanau.FileService.FileService;
import Bazhanau.FileService.IFileService;
import Bazhanau.FileWindow.AbstractSaveButtonListener;
import Bazhanau.Task2.MainWindow;

import java.io.File;
import java.io.IOException;

public class SaveButtonListener extends AbstractSaveButtonListener {
    private IFileService fileService = new FileService();
    private MainWindow _this;

    public SaveButtonListener(MainWindow _this) {
        super(_this);
        this._this = _this;
    }

    @Override
    public void save(File file) {
        try {
            fileService.writeObject(file, _this.getData());
        } catch (IOException e) {
            _this.catchException(e);
        }
    }
}