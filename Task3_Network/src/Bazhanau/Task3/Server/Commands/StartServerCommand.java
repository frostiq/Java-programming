package Bazhanau.Task3.Server.Commands;

import Bazhanau.Task3.Server.IServerWindow;
import Bazhanau.Task3.Server.ServerBeacon;

public class StartServerCommand extends Command {
    public StartServerCommand(IServerWindow wnd) {
        super(wnd);
    }

    @Override
    public void execute() {
        wnd.setServerBeacon(new ServerBeacon(wnd));
        wnd.getServerBeacon().start();
        wnd.getStartStopButton().setText("Stop server");
    }

    @Override
    public void cancel() {
        wnd.getServerBeacon().interrupt();
        wnd.setServerBeacon(null);
        wnd.getStartStopButton().setText("Start server");
    }
}
