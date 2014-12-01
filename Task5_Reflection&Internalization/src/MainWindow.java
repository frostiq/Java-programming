import Bazhanau.Logging.ICatcher;
import Bazhanau.Logging.MessageBoxCatcher;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Properties;

/*
TODO:
    -labeled borders
    -appropriate size
    -i18n

 */
public class MainWindow extends JFrame implements InvokerCaller {
    private Properties properties = new Properties();
    private ICatcher catcher = new MessageBoxCatcher(this);
    private Collection<Method> methods;
    private JPanel radioPanel = new JPanel();
    private JPanel methodPanel = new JPanel();
    private JButton callButton = new JButton("Call");
    private Invoker invoker = null;

    public MainWindow() throws HeadlessException {
        initMethods();

        setLayout(new BorderLayout());
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        methodPanel.setLayout(new BoxLayout(methodPanel, BoxLayout.X_AXIS));
        add(radioPanel, BorderLayout.WEST);
        add(methodPanel, BorderLayout.CENTER);
        add(callButton, BorderLayout.NORTH);
        ButtonGroup buttonGroup = new ButtonGroup();
        for (Method method : methods) {
            JRadioButton button = new JRadioButton(method.getName());
            button.addActionListener(new MethodButtonListener(methodPanel, method, this));
            radioPanel.add(button, BorderLayout.WEST);
            buttonGroup.add(button);
        }

        callButton.addActionListener(e -> {
            if (invoker != null) {
                try {
                    Object result = invoker.invoke();
                    if (result == null) result = "null";
                    JOptionPane.showMessageDialog(this, result.toString(), "Method's result", JOptionPane.INFORMATION_MESSAGE);
                } catch (IllegalAccessException | InvocationTargetException e1) {
                    catcher.catchException(e1);
                }
            }
        });

        setSize(640, 480);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    @Override
    public Invoker getInvoker() {
        return invoker;
    }

    @Override
    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    private void initMethods() {
        try (FileInputStream inputStream = new FileInputStream("Resources/calc.properties")) {
            properties.load(inputStream);
            methods = MethodsParser.parse(properties.getProperty("methods"));
        } catch (Exception e) {
            catcher.catchException(e);
        }
    }
}
