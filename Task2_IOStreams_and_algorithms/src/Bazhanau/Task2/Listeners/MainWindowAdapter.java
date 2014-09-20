package Bazhanau.Task2.Listeners;

import Bazhanau.FileService;
import Bazhanau.IFileService;
import Bazhanau.Listeners.AbstractMainWindowAdapter;
import Bazhanau.Task2.MainWindow;

import java.io.File;
import java.io.IOException;

public class MainWindowAdapter extends AbstractMainWindowAdapter {
    private MainWindow _this;
    private IFileService fileService = new FileService();

    public MainWindowAdapter(MainWindow _this) {
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