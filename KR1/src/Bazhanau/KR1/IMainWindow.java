package Bazhanau.KR1;

import Bazhanau.Logging.ICatcher;

import javax.swing.*;
import java.awt.*;

public interface IMainWindow {
    ICatcher getCatcher();

    int getRectWidth();

    void setRectWidth(int rectWidth);

    int getRectHeight();

    void setRectHeight(int rectHeight);

    Color getRectColor();

    void setRectColor(Color rectColor);

    JPanel getPanel();
}
