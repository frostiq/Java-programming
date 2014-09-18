package Bazhanau;

import javax.swing.*;

public class Catcher {
    public Catcher() {
    }

    public static void catchException(Exception e) {
        JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
