package Bazhanau.Task2.Listeners;

import Bazhanau.FileService;
import Bazhanau.IFileService;
import Bazhanau.Listeners.AbstractMainWindowAdapter;
import Bazhanau.Task2.MainWindow;

import java.io.File;

public class MainWindowAdapter extends AbstractMainWindowAdapter {
    private MainWindow _this;
    private IFileService fileService = new FileService();

    public MainWindowAdapter(MainWindow _this) {
        super(_this);
        this._this = _this;
    }

    @Override
    public void Save(File file) {
    }
}