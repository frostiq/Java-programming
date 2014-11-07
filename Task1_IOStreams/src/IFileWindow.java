import java.io.File;

public interface IFileWindow {

    public abstract String getSaveQuestion();

    public abstract int showOpenDialog();

    public abstract int showSaveDialog();

    public abstract File getLastSelectedFile();
}
