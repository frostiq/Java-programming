import Bazhanau.Logging.ICatcher;
import Bazhanau.Logging.MessageBoxCatcher;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;

/*
TODO:
    -appropriate size
    -i18n experiments
 */

public class MainWindow extends JFrame implements InvokerCaller {
    private ResourceManager resourceManager = ResourceManager.INSTANCE;
    private ICatcher catcher = new MessageBoxCatcher(this);
    private Collection<Method> methods;
    private JPanel radioPanel = new JPanel();
    private JPanel methodPanel = new JPanel();
    private JButton callButton = new JButton(resourceManager.getString("call"));
    private Invoker invoker = null;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu(ResourceManager.INSTANCE.getString("language"));
    private ArrayList<JMenuItem> languageMenuItems = new ArrayList<>();

    public MainWindow() throws HeadlessException {
        initMethods();
        initLanguageMenu();

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
                    JOptionPane.showMessageDialog(
                            this,
                            result.toString(),
                            resourceManager.getString("method.s.result"),
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e1) {
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

    private void initLanguageMenu() {
        setJMenuBar(menuBar);
        menuBar.add(menu);
        languageMenuItems.add(new JMenuItem("en"));
        languageMenuItems.add(new JMenuItem("be"));
        for (JMenuItem jMenuItem : languageMenuItems) {
            jMenuItem.addActionListener(e -> {
                ResourceManager.INSTANCE.changeLocale(new Locale(jMenuItem.getText()));
                JOptionPane.showMessageDialog(
                        MainWindow.this,
                        ResourceManager.INSTANCE.getString("reload.app"),
                        "",
                        JOptionPane.WARNING_MESSAGE);
            });
            menu.add(jMenuItem);
        }
    }

    private void initMethods() {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("Resources/calc.properties")) {
            properties.load(inputStream);
            methods = MethodsParser.parse(properties.getProperty("methods"));
        } catch (Exception e) {
            catcher.catchException(e);
        }
    }

    @Override
    public Invoker getInvoker() {
        return invoker;
    }

    @Override
    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }
}
