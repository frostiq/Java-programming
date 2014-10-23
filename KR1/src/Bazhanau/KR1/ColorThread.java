package Bazhanau.KR1;

public class ColorThread extends Thread {
    private IMainWindow window;
    private long sleepMilliseconds = 2500;

    public ColorThread(IMainWindow window) {
        this.window = window;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                sleep(sleepMilliseconds);
                synchronized (window.getPanel()) {
                    

                    window.getPanel().validate();
                    window.getPanel().repaint();
                }
            }
        } catch (InterruptedException e) {
            window.getCatcher().catchException(e);
        }

    }
}
