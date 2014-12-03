import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class MethodButtonListener implements ActionListener {
    JPanel panel;
    Method method;
    InvokerCaller wnd;

    public MethodButtonListener(JPanel panel, Method method, InvokerCaller wnd) {
        this.panel = panel;
        this.method = method;
        this.wnd = wnd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.removeAll();
        ArrayList<JTextField> textParams = new ArrayList<>();
        JTextField thisTextParam = null;
        if (!Modifier.isStatic(method.getModifiers())) {
            thisTextParam = new JTextField();
            thisTextParam.setBorder(BorderFactory.createTitledBorder(method.getDeclaringClass().getName() + ".this"));
            panel.add(thisTextParam);
        }

        for (Class param : method.getParameterTypes()) {
            JTextField textField = new JTextField();
            textField.setBorder(BorderFactory.createTitledBorder(param.getName()));
            textParams.add(textField);
            panel.add(textField);
        }
        panel.revalidate();
        panel.repaint();

        wnd.setInvoker(new Invoker(method, textParams, thisTextParam));
    }
}
