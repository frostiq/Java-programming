package Bazhanau;

import java.io.File;

public interface IFileService {
    public abstract String readText(File paramFile);

    public abstract void writeText(File paramFile, String paramString);

    public abstract String encodeText(String paramString1, String paramString2);

    void writeObject(File file, Object object);

    Object readObject(File file);
}
