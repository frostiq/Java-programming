package Bazhanau.FileService;

import java.io.File;
import java.io.IOException;

public interface IFileService {
    public abstract String readText(File paramFile) throws IOException;

    public abstract void writeText(File paramFile, String paramString) throws IOException;

    public abstract String encodeText(String paramString1, String paramString2);

    void writeObject(File file, Object object) throws IOException;

    Object readObject(File file) throws IOException, ClassNotFoundException;
}
