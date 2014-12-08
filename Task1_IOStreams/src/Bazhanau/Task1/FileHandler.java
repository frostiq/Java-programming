package Bazhanau.Task1;

import Bazhanau.FileService.FileService;
import Bazhanau.FileWindow.IFileHandler;

import java.io.File;
import java.io.IOException;

public class FileHandler implements IFileHandler {
    private MainWindow wnd;
    private FileService fileService = new FileService();

    public FileHandler(MainWindow wnd) {
        this.wnd = wnd;
    }

    @Override
    public void open(File file) {
        try {
            wnd.setFileText(fileService.readText(file));
        } catch (IOException e) {
            wnd.catchException(e);
        }
    }

    @Override
    public void save(File file) {
        try {
            fileService.writeText(file, wnd.getFileText());
        } catch (IOException e) {
            wnd.catchException(e);
        }
    }
}
