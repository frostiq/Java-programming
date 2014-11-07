package Bazhanau.Task3.Server.Commands;

import Bazhanau.Task3.Server.IServerWindow;

public abstract class Command {
    public abstract void execute();

    public abstract void cancel();

    protected IServerWindow wnd;

    protected Command(IServerWindow wnd) {
        this.wnd = wnd;
    }
}
