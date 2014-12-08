package Bazhanau.FileWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class OpenButtonListener implements ActionListener {
    private FileMainWindow wnd;

    public OpenButtonListener(FileMainWindow wnd) {
        this.wnd = wnd;
    }

    public void actionPerformed(ActionEvent e) {
        if (wnd.showOpenDialog() == 0) {
            wnd.getFileHandler().open(wnd.getLastSelectedFile());
            wnd.setIsSaved(true);
        }
    }
}