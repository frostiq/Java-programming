package Bazhanau.Task1.Listeners;

import Bazhanau.FileWindow.FileMainWindow;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextAreaListener
        implements DocumentListener {
    private FileMainWindow _this;

    public TextAreaListener(FileMainWindow _this) {
        this._this = _this;
    }

    public void insertUpdate(DocumentEvent e) {
        _this.setIsSaved(false);
    }

    public void removeUpdate(DocumentEvent e) {
        _this.setIsSaved(false);
    }

    public void changedUpdate(DocumentEvent e) {
    }
}

