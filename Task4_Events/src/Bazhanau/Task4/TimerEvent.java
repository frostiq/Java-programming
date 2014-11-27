package Bazhanau.Task4;

import java.awt.*;
import java.util.Date;

/**
 * A custom event class.
 */
class TimerEvent extends AWTEvent {
    public static final int TIMER_EVENT = AWTEvent.RESERVED_ID_MAX + 5555;
    private Date currentTime;

    public TimerEvent(Timer t, Date currentTime) {
        super(t, TIMER_EVENT);
        this.currentTime = currentTime;
    }

    public Date getCurrentTime() {
        return currentTime;
    }
}
