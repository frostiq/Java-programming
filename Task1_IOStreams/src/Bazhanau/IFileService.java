package Bazhanau;

import java.io.File;

public abstract interface IFileService {
    public abstract String readFile(File paramFile);

    public abstract void writeText(File paramFile, String paramString);

    public abstract String encodeText(String paramString1, String paramString2);
}
