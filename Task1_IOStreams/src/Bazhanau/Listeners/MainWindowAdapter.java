package Bazhanau.Listeners;

import Bazhanau.FileService;
import Bazhanau.IFileService;
import Bazhanau.Task1.IMainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindowAdapter extends WindowAdapter {
    private IMainWindow _this;
    private IFileService fileService = new FileService();

    public MainWindowAdapter(IMainWindow _this) {
        this._this = _this;
    }

    public void windowClosing(WindowEvent e) {
        if (_this.getFileText().isEmpty()) {
            System.exit(0);
        }
        int res = JOptionPane.showConfirmDialog(null, _this.getSaveQuestion(), "", JOptionPane.YES_NO_CANCEL_OPTION);
        if (res == JOptionPane.CANCEL_OPTION) {
            return;
        }
        if (res == JOptionPane.YES_OPTION) {
            if (_this.showSaveDialog() == JFileChooser.APPROVE_OPTION) {
                fileService.writeText(_this.getLastSelectedFile(), _this.getFileText());
            } else {
                return;
            }
        }
        System.exit(0);
    }
}