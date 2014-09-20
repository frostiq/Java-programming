package Bazhanau;

import javax.swing.*;
import java.awt.*;

public class Catcher implements ICatcher {
    private Component component;

    public Catcher(Component component) {
        this.component = component;
    }

    @Override
    public void catchException(Exception e) {
        JOptionPane.showMessageDialog(component, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
