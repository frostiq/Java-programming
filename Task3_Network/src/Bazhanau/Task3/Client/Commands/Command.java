package Bazhanau.Task3.Client.Commands;

import Bazhanau.Task3.Client.IClientWindow;

import java.io.IOException;

public abstract class Command {
    protected IClientWindow wnd;

    protected Command(IClientWindow wnd) {
        this.wnd = wnd;
    }

    public abstract void execute() throws IOException;

    public void cancel() {
    }
}
