package Bazhanau.KR1;

import Bazhanau.Logging.ICatcher;
import Bazhanau.Logging.MessageBoxCatcher;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements IMainWindow {
    private Thread diamThread = new DiamThread(this);
    private Thread colorThread = new ColorThread(this);

    private int rectWidth = 100;
    private int rectHeight = 100;
    private Color rectColor = Color.BLUE;
    private JPanel panel = new JPanel() {
        @Override
        protected synchronized void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(rectColor);
            g.fillRect(getRectX(), getRectY(), rectWidth, rectHeight);
        }
    };
    private ICatcher catcher = new MessageBoxCatcher(this);

    public MainWindow() {
        add(panel);
        setSize(800, 400);
        setLocationByPlatform(true);
        setVisible(true);
        diamThread.start();
        colorThread.start();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new MainWindow();
    }

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

    private int getRectX() {
        return panel.getWidth() / 2 - rectWidth / 2;
    }

    private int getRectY() {
        return panel.getHeight() / 2 - rectHeight / 2;
    }
}
