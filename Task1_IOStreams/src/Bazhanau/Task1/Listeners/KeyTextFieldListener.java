package Bazhanau.Task1.Listeners;

import Bazhanau.Task1.MainWindow;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class KeyTextFieldListener
        implements DocumentListener {
    private MainWindow _this;

    public KeyTextFieldListener(MainWindow _this) {
        this._this = _this;
    }

    public void insertUpdate(DocumentEvent e) {
        _this.enableEncoding(_this.getKey().length() != 0);
    }

    public void removeUpdate(DocumentEvent e) {
        _this.enableEncoding(_this.getKey().length() != 0);
    }

    public void changedUpdate(DocumentEvent e) {
    }
}

