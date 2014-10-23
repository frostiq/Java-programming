package Bazhanau.KR1;

public class DiamThread extends Thread {
    private IMainWindow window;
    private long sleepMilliseconds = 1500;

    public DiamThread(IMainWindow window) {
        this.window = window;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                sleep(sleepMilliseconds);
                synchronized (window.getPanel()) {
                    int newHeight = window.getRectHeight() + 10;
                    int newWidth = window.getRectWidth() + 10;

                    if (newHeight >= window.getHeight() || newWidth >= window.getWidth()) {
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
