package Bazhanau.KR1;

import Bazhanau.Catcher;
import Bazhanau.ICatcher;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements IMainWindow {
    private Thread diamThread = new DiamThread(this);
    private Thread colorThread = new ColorThread(this);

    private int rectWidth = 100;
    private int rectHeight = 100;
    private Color rectColor = Color.BLUE;

    private ICatcher catcher = new Catcher(this);

    @Override
    public ICatcher getCatcher() {
        return catcher;
    }

    @Override
    public int getRectWidth() {
        return rectWidth;
    }

    @Override
    public void setRectWidth(int rectWidth) {
        this.rectWidth = rectWidth;
    }

    @Override
    public int getRectHeight() {
        return rectHeight;
    }

    @Override
    public void setRectHeight(int rectHeight) {
        this.rectHeight = rectHeight;
    }

    @Override
    public Color getRectColor() {
        return rectColor;
    }

    @Override
    public void setRectColor(Color rectColor) {
        this.rectColor = rectColor;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    private JPanel panel = new JPanel() {
        @Override
        protected synchronized void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(rectColor);
            g.fillRect(getRectX(), getRectY(), rectWidth, rectHeight);
        }
    };

    public MainWindow() {
        add(panel);
        setSize(800, 400);
        setLocationByPlatform(true);
        setVisible(true);
        diamThread.start();
        colorThread.start();
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    private int getRectX() {
        return getWidth() / 2 - rectWidth / 2;
    }

    private int getRectY() {
        return getHeight() / 2 - rectHeight / 2;
    }
}
