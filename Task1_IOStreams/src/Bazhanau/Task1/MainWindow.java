package Bazhanau.Task1;

import Bazhanau.Listeners.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainWindow extends JFrame implements IMainWindow {
    private JMenu menu = new JMenu("Файл");
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem openMenuItem = new JMenuItem("Адчыніць файл...");
    private JMenuItem saveMenuItem = new JMenuItem("Захаваць файл...");
    private JPanel controlPanel = new JPanel();
    private JTextField keyTextField = new JTextField(10);
    private JLabel keyLabel = new JLabel("Ключ:");
    private JButton encodeButton = new JButton("Кадаваць тэкст па ключы");
    private JTextArea textArea = new JTextArea();
    private JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    private String saveQuestion = "Захаваць файл перад выхадам?";

    public MainWindow() {
        getContentPane().setLayout(new BorderLayout());

        setJMenuBar(this.menuBar);
        this.menuBar.add(this.menu);
        this.menu.add(this.openMenuItem);
        this.menu.add(this.saveMenuItem);

        add(this.controlPanel, "North");
        this.controlPanel.setLayout(new FlowLayout());
        this.controlPanel.add(this.keyLabel);
        this.controlPanel.add(this.keyTextField);
        this.controlPanel.add(this.encodeButton);
        this.encodeButton.setEnabled(false);

        add(this.textArea, "Center");

        this.openMenuItem.addActionListener(new OpenButtonListener(this));
        this.saveMenuItem.addActionListener(new SaveButtonListener(this));
        this.keyTextField.getDocument().addDocumentListener(new KeyTextFieldListener(this));
        addWindowListener(new MainWindowAdapter(this));
        this.encodeButton.addActionListener(new EncodeButtonListener(this));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(800, 400);
        setLocationByPlatform(true);
        setVisible(true);
    }

    public void enableEncoding(boolean b) {
        this.encodeButton.setEnabled(b);
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

    public String getFileText() {
        return this.textArea.getText();
    }

    public void setFileText(String text) {
        this.textArea.setText(text);
    }

    public String getKey() {
        return this.keyTextField.getText();
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
