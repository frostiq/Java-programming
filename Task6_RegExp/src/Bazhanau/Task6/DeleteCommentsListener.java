package Bazhanau.Task6;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteCommentsListener implements ActionListener {
    private final String stringRegex = "\'([^\'\\\\]|\\\\.)*\'";
    private final String commentsRegex = "(?s)(?<!('[^']?))(\\{.*?\\})|(//.*?\r\n)(?!([^']?'))";
    private MainWindow wnd;

    public DeleteCommentsListener(MainWindow wnd) {
        this.wnd = wnd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = wnd.textArea.getText();
        text = text.replaceAll(commentsRegex, "");
        wnd.textArea.setText(text);
    }
}
