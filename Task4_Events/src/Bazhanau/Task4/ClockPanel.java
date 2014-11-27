package Bazhanau.Task4;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClockPanel extends JPanel {
    int lastxs, lastys, lastxm,
            lastym, lastxh, lastyh;  // Dimensions used to draw hands
    SimpleDateFormat formatter;  // Formats the date displayed
    String lastdate;             // String to hold date displayed
    Font clockFaceFont;          // Font for number display on clock
    Date currentDate;            // Used to get date to display
    Color handColor;             // Color of main hands and dial
    Color numberColor;           // Color of second hand and numbers

    public ClockPanel() throws HeadlessException {
        lastxs = lastys = lastxm = lastym = lastxh = lastyh = 0;
        formatter = new SimpleDateFormat("dd.MM.yyyy, E, hh:mm:ss a", Locale.getDefault());
        currentDate = new Date();
        lastdate = formatter.format(currentDate);
        clockFaceFont = new Font("Serif", Font.PLAIN, 14);
        handColor = Color.blue;
        numberColor = Color.darkGray;
    }

    // Paint is the main part of the program
    @Override
    public void paint(Graphics g) {
        int xh, yh, xm, ym, xs, ys, s = 0, m = 0, h = 12, xcenter, ycenter;
        String today;

        currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("s", Locale.getDefault());
        try {
            s = Integer.parseInt(formatter.format(currentDate));
        } catch (NumberFormatException n) {
            s = 0;
        }
        formatter.applyPattern("m");
        try {
            m = Integer.parseInt(formatter.format(currentDate));
        } catch (NumberFormatException n) {
            m = 0;
        }
        formatter.applyPattern("h");
        try {
            h = Integer.parseInt(formatter.format(currentDate));
        } catch (NumberFormatException n) {
            h = 12;
        }
        formatter.applyPattern("dd.MM.yyyy, E, hh:mm:ss a");
        today = formatter.format(currentDate);
        xcenter = 80;
        ycenter = 55;


        xs = (int) (Math.cos(s * Math.PI / 30 - Math.PI / 2) * 45 + xcenter);
        ys = (int) (Math.sin(s * Math.PI / 30 - Math.PI / 2) * 45 + ycenter);
        xm = (int) (Math.cos(m * Math.PI / 30 - Math.PI / 2) * 40 + xcenter);
        ym = (int) (Math.sin(m * Math.PI / 30 - Math.PI / 2) * 40 + ycenter);
        xh = (int) (Math.cos((h * 30 + m / 2) * Math.PI / 180 - Math.PI / 2) * 30 + xcenter);
        yh = (int) (Math.sin((h * 30 + m / 2) * Math.PI / 180 - Math.PI / 2) * 30 + ycenter);

        // Draw the circle and numbers

        g.setFont(clockFaceFont);
        g.setColor(handColor);
        g.drawOval(xcenter - 50, ycenter - 50, 100, 100);
        g.setColor(numberColor);
        g.drawString("9", xcenter - 45, ycenter + 3);
        g.drawString("3", xcenter + 40, ycenter + 3);
        g.drawString("12", xcenter - 6, ycenter - 37);
        g.drawString("6", xcenter - 3, ycenter + 45);

        // Erase if necessary, and redraw

        g.setColor(getBackground());

        if (xs != lastxs || ys != lastys) {
            g.drawLine(xcenter, ycenter, lastxs, lastys);
            g.drawString(lastdate, 1, 125);
        }
        if (xm != lastxm || ym != lastym) {
            g.drawLine(xcenter, ycenter - 1, lastxm, lastym);
            g.drawLine(xcenter - 1, ycenter, lastxm, lastym);
        }
        if (xh != lastxh || yh != lastyh) {
            g.drawLine(xcenter, ycenter - 1, lastxh, lastyh);
            g.drawLine(xcenter - 1, ycenter, lastxh, lastyh);
        }

        g.setColor(numberColor);
        g.drawString("", 1, 125);
        g.drawString(today, 1, 125);
        g.drawLine(xcenter, ycenter, xs, ys);
        g.setColor(handColor);
        g.drawLine(xcenter, ycenter - 1, xm, ym);
        g.drawLine(xcenter - 1, ycenter, xm, ym);
        g.drawLine(xcenter, ycenter - 1, xh, yh);
        g.drawLine(xcenter - 1, ycenter, xh, yh);
        lastxs = xs;
        lastys = ys;
        lastxm = xm;
        lastym = ym;
        lastxh = xh;
        lastyh = yh;
        lastdate = today;
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

}
