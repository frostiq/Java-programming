package Bazhanau;

import javax.swing.*;
import java.io.File;

public abstract class FileMainWindow extends JFrame {
    protected JMenu menu = new JMenu("Файл");
    protected JMenuBar menuBar = new JMenuBar();
    protected JMenuItem openMenuItem = new JMenuItem("Адчыніць файл...");
    protected JMenuItem saveMenuItem = new JMenuItem("Захаваць файл...");
    protected JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    protected String saveQuestion = "Захаваць файл перад выхадам?";

    public FileMainWindow() {
        setJMenuBar(this.menuBar);
        this.menuBar.add(this.menu);
        this.menu.add(this.openMenuItem);
        this.menu.add(this.saveMenuItem);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(800, 400);
        setLocationByPlatform(true);
        setVisible(true);
    }

    public int showOpenDialog() {
        return this.fileChooser.showOpenDialog(this);
    }

    public int showSaveDialog() {
        return this.fileChooser.showSaveDialog(this);
    }

    public String getSaveQuestion() {
        return this.saveQuestion;
    }

    public File getLastSelectedFile() {
        return this.fileChooser.getSelectedFile();
    }

    public abstract boolean hasNoInfoToSave();
}