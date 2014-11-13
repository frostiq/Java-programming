package Bazhanau.Logging;

import javax.swing.*;
import java.awt.*;

public class MessageBoxCatcher implements ICatcher {
    private Component component;

    public MessageBoxCatcher(Component component) {
        this.component = component;
    }

    @Override
    public void catchException(Exception e) {
        JOptionPane.showMessageDialog(component, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
