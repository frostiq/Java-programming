package Bazhanau.FileWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SaveButtonListener implements ActionListener {
    private FileMainWindow wnd;

    public SaveButtonListener(FileMainWindow wnd) {
        this.wnd = wnd;
    }

    public void actionPerformed(ActionEvent e) {
        if (wnd.showSaveDialog() == 0) {
            wnd.setIsSaved(true);
            wnd.getFileHandler().save(wnd.getLastSelectedFile());
        }
    }
}