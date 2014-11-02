package Bazhanau.Task3.Client.Commands;

import Bazhanau.Task3.Client.IClientWindow;

public abstract class Command {
    public abstract void execute();

    public void cancel() {
    }

    protected IClientWindow wnd;

    protected Command(IClientWindow wnd) {
        this.wnd = wnd;
    }
}
