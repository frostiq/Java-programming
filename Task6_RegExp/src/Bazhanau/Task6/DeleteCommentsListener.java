package Bazhanau.Task6;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteCommentsListener implements ActionListener {
    private final String stringRegex1 = "\'(([^\'\\\\]|\\\\.)*?)\\{(([^'\\\\]|\\\\.)*?)\\}(([^'\\\\]|\\\\.)*?)\'";
    private final String stringRegex2 = "\'(([^\'\\\\]|\\\\.)*?)<(([^'\\\\]|\\\\.)*?)>(([^'\\\\]|\\\\.)*?)\'";
    private final String commentsRegex = "(?s)(\\{.*?\\})|(//.*?\r\n)";
    private MainWindow wnd;

    public DeleteCommentsListener(MainWindow wnd) {
        this.wnd = wnd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = wnd.textArea.getText();

        text = text.replaceAll(stringRegex1, "\'$1<$3>$5\'");
        text = text.replaceAll(commentsRegex, "");
        text = text.replaceAll(stringRegex2, "\'$1{$3}$5\'");
        wnd.textArea.setText(text);
    }
}
