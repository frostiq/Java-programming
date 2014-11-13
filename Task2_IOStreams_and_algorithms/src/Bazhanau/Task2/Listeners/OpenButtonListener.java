package Bazhanau.Task2.Listeners;

import Bazhanau.FileService.FileService;
import Bazhanau.FileService.IFileService;
import Bazhanau.FileWindow.AbstractOpenButtonListener;
import Bazhanau.Task2.MainWindow;
import Bazhanau.Task2.Models.StudentModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
            _this.setData((ArrayList<StudentModel>) fileService.readObject(file));
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            _this.catchException(e);
        }
    }
}