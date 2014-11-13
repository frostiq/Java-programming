package Bazhanau.Task1;

import Bazhanau.FileWindow.FileMainWindow;
import Bazhanau.Task1.Listeners.*;

import javax.swing.*;
import java.awt.*;

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

        super.openMenuItem.addActionListener(new OpenButtonListener(this));
        super.saveMenuItem.addActionListener(new SaveButtonListener(this));
        this.keyTextField.getDocument().addDocumentListener(new KeyTextFieldListener(this));
        this.textArea.getDocument().addDocumentListener(new TextAreaListener(this));
        addWindowListener(new MainWindowAdapter(this));
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
    public boolean hasNoInfoToSave() {
        return this.textArea.getText().isEmpty();
    }
}
