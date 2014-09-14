package Bazhanau.Task1;

import java.io.File;

public abstract interface IMainWindow {
    public abstract void enableEncoding(boolean paramBoolean);

    public abstract String getSaveQuestion();

    public abstract int showOpenDialog();

    public abstract int showSaveDialog();

    public abstract File getLastSelectedFile();

    public abstract String getFileText();

    public abstract void setFileText(String paramString);

    public abstract String getKey();
}
