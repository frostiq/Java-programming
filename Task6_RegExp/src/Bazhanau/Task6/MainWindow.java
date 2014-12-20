package Bazhanau.Task6;

import Bazhanau.FileService.FileService;
import Bazhanau.FileWindow.DocumentChangesListener;
import Bazhanau.FileWindow.FileMainWindow;
import Bazhanau.FileWindow.IFileHandler;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends FileMainWindow {
    JTextArea textArea = new JTextArea();
    JMenuItem deleteCommentsMenuItem = new JMenuItem("Выдаліць каментары");

    public MainWindow() {
        super.setTitle("Regular expressions");
        add(new JScrollPane(textArea));
        menu.add(this.deleteCommentsMenuItem);
        deleteCommentsMenuItem.addActionListener(new DeleteCommentsListener(this));
        textArea.getDocument().addDocumentListener(new DocumentChangesListener(this));

    }

    public static void main(String[] args) {
        new MainWindow();
    }

    @Override
    public boolean hasInfoToSave() {
        return !textArea.getText().isEmpty();
    }

    @Override
    public IFileHandler getFileHandler() {
        return new FileHandler();
    }

    private class FileHandler implements IFileHandler {
        private FileService fileService = new FileService();

        @Override
        public void open(File file) {
            try {
                textArea.setText(fileService.readText(file));
            } catch (IOException e) {
                catchException(e);
            }
        }

        @Override
        public void save(File file) {
            try {
                fileService.writeText(file, textArea.getText());
            } catch (IOException e) {
                catchException(e);
            }
        }
    }
}
