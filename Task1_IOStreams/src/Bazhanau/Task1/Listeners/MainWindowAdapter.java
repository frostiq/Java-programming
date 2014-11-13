package Bazhanau.Task1.Listeners;

import Bazhanau.FileService.FileService;
import Bazhanau.FileService.IFileService;
import Bazhanau.FileWindow.AbstractMainWindowAdapter;
import Bazhanau.Task1.MainWindow;

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
            fileService.writeText(file, _this.getFileText());
        } catch (IOException e) {
            _this.catchException(e);
        }
    }
}