package Bazhanau;

public class LogCatcher implements ICatcher {
    private ILogWindow window;

    public LogCatcher(ILogWindow window) {
        this.window = window;
    }

    @Override
    public void catchException(Exception e) {
        window.printToLog(e.toString());
    }
}
