package Bazhanau.KR1;

import javax.swing.*;

public class DiamThread extends Thread {
    private IMainWindow window;
    private long sleepMilliseconds = 1500;
    private int sizeIncrement = 10;
    private int border = 100;

    public DiamThread(IMainWindow window) {
        this.window = window;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                sleep(sleepMilliseconds);
                JPanel panel = window.getPanel();
                synchronized (panel) {
                    int newHeight = window.getRectHeight() + sizeIncrement;
                    int newWidth = window.getRectWidth() + sizeIncrement;

                    if (newHeight - border >= panel.getHeight() || newWidth - border >= panel.getWidth()) {
                        interrupt();
                    }

                    window.setRectHeight(newHeight);
                    window.setRectWidth(newWidth);
                    window.getPanel().validate();
                    window.getPanel().repaint();
                }
            }
        } catch (InterruptedException e) {
            window.getCatcher().catchException(e);
        }

    }
}
