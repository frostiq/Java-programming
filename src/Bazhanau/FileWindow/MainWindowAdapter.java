package Bazhanau.FileWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class MainWindowAdapter extends WindowAdapter {
    private FileMainWindow wnd;

    public MainWindowAdapter(FileMainWindow wnd) {
        this.wnd = wnd;
    }

    public void windowClosing(WindowEvent e) {
        if (!wnd.hasInfoToSave() || wnd.isSaved()) {
            System.exit(0);
        }
        int res = JOptionPane.showConfirmDialog(wnd, wnd.getSaveQuestion(), "", JOptionPane.YES_NO_CANCEL_OPTION);
        if (res == JOptionPane.CANCEL_OPTION) {
            return;
        }
        if (res == JOptionPane.YES_OPTION) {
            if (wnd.showSaveDialog() == JFileChooser.APPROVE_OPTION) {
                wnd.getFileHandler().save(wnd.getLastSelectedFile());
                wnd.setIsSaved(true);
            } else {
                return;
            }
        }
        System.exit(0);
    }
}