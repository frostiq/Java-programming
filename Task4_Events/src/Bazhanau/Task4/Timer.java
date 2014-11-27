package Bazhanau.Task4;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.util.Date;
import java.util.EventListener;

/**
 * A custom timer class that is the source of timer events.
 */
class Timer extends JComponent implements Runnable {
    private int interval;
    private EventListenerList listeners;

    public Timer(int milliseconds) {
        listenerList = new EventListenerList();
        interval = milliseconds;
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Adds a timer listener
     *
     * @param listener the listener to add
     */
    public void addTimerListener(ITimerListener listener) {
        listenerList.add(ITimerListener.class, listener);
    }

    /**
     * Removes a timer listener
     *
     * @param listener the listener to remove
     */
    public void removeTimerListener(ITimerListener listener) {
        listenerList.remove(ITimerListener.class, listener);
    }

    /**
     * Posts a new timer event every <code>interval</code>
     * milliseconds.
     */
    public void run() {
        while (true) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
            }

            TimerEvent event = new TimerEvent(this, new Date());

            EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
            queue.postEvent(event);
        }
    }

    public void processEvent(AWTEvent event) {
        if (event instanceof TimerEvent) {
            EventListener[] listeners = listenerList.getListeners(ITimerListener.class);
            for (EventListener listener : listeners) {
                ((ITimerListener) listener).timeElapsed((TimerEvent) event);
            }
        } else
            super.processEvent(event);
    }
}
