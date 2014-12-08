package Bazhanau.Task6;

import Bazhanau.FileWindow.FileMainWindow;
import Bazhanau.FileWindow.IFileHandler;

import javax.swing.*;

public class MainWindow extends FileMainWindow {
    JTextArea textArea = new JTextArea();
    JMenuItem deleteCommentsMenuItem = new JMenuItem("Выдаліць каментары");

    public MainWindow() {
        super.setTitle("Regular expressions");
        add(new JScrollPane(textArea));
        menu.add(this.deleteCommentsMenuItem);
        deleteCommentsMenuItem.addActionListener(new DeleteCommentsListener());

    }

    public static void main(String[] args) {
        new MainWindow();
    }

    @Override
    public boolean hasNoInfoToSave() {
        return textArea.getText().isEmpty();
    }

    @Override
    public IFileHandler getFileHandler() {
        return new FileHandler(this);
    }
}
