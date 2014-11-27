package Bazhanau.Task4;

import Bazhanau.Sound;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Clock extends JFrame implements Runnable, ITimerListener {
    Thread timer;                // The thread that displays clock
    SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
    private Timer eventTimer = new Timer(1000 * 15);
    private ClockPanel clockPanel = new ClockPanel();

    public Clock() throws HeadlessException {
        super("Гадзiннiк");

        add(clockPanel);
        setSize(250, 250);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        eventTimer.addTimerListener(this);
        timer = new Thread(this);
        timer.start();
    }

    public static void main(String[] args) {
        new Clock();
    }

    public void run() {
        Thread me = Thread.currentThread();
        while (timer == me) {
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
            }
            validate();
            repaint();
        }
    }

    @Override
    public void timeElapsed(TimerEvent event) {
        Sound.playSound("Clock.wav");
        JOptionPane.showMessageDialog(this, dateFormat.format(event.getCurrentTime()));
    }
}



