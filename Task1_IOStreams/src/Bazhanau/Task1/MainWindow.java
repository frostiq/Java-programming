package Bazhanau.Task1;

import Bazhanau.FileService.FileService;
import Bazhanau.FileWindow.DocumentChangesListener;
import Bazhanau.FileWindow.FileMainWindow;
import Bazhanau.FileWindow.IFileHandler;
import Bazhanau.Task1.Listeners.EncodeButtonListener;
import Bazhanau.Task1.Listeners.KeyTextFieldListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends FileMainWindow {
    private JPanel controlPanel = new JPanel();
    private JTextField keyTextField = new JTextField(10);
    private JLabel keyLabel = new JLabel("Ключ:");
    private JButton encodeButton = new JButton("Кадаваць тэкст па ключы");
    private JTextArea textArea = new JTextArea();


    public MainWindow() {
        getContentPane().setLayout(new BorderLayout());

        add(this.controlPanel, "North");
        this.controlPanel.setLayout(new FlowLayout());
        this.controlPanel.add(this.keyLabel);
        this.controlPanel.add(this.keyTextField);
        this.controlPanel.add(this.encodeButton);
        this.encodeButton.setEnabled(false);
        add(new JScrollPane(this.textArea), "Center");

        this.keyTextField.getDocument().addDocumentListener(new KeyTextFieldListener(this));
        this.textArea.getDocument().addDocumentListener(new DocumentChangesListener(this));
        this.encodeButton.addActionListener(new EncodeButtonListener(this));
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    public void enableEncoding(boolean b) {
        this.encodeButton.setEnabled(b);
    }

    public String getFileText() {
        return this.textArea.getText();
    }

    public void setFileText(String text) {
        this.textArea.setText(text);
    }

    public String getKey() {
        return this.keyTextField.getText();
    }

    @Override
    public boolean hasInfoToSave() {
        return !this.textArea.getText().isEmpty();
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
                setFileText(fileService.readText(file));
            } catch (IOException e) {
                catchException(e);
            }
        }

        @Override
        public void save(File file) {
            try {
                fileService.writeText(file, getFileText());
            } catch (IOException e) {
                catchException(e);
            }
        }
    }
}
