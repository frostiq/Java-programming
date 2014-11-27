package Bazhanau.Task4;

import java.util.EventListener;

/**
 * A custom event listener interface.
 */
interface ITimerListener extends EventListener {
    public void timeElapsed(TimerEvent event);
}
