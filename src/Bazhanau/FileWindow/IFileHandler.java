package Bazhanau.FileWindow;

import java.io.File;

public interface IFileHandler {
    public abstract void open(File file);

    public abstract void save(File file);
}
