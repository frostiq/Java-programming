import Bazhanau.Logging.MessageBoxCatcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public enum ResourceManager {
    INSTANCE;

    private final String resourceName = "lang";

    private final String settingsName = "resources/calc.properties";

    private ResourceBundle resourceBundle;

    private Locale locale = null;

    private ResourceManager() {
        resourceBundle = ResourceBundle.getBundle(resourceName, getLocale());
    }

    public void changeLocale(Locale locale) {
        Properties properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(settingsName);

            properties.load(inputStream);
            inputStream.close();

            properties.setProperty("locale", locale.getLanguage());

            FileOutputStream outputStream = new FileOutputStream(settingsName);
            properties.store(outputStream, null);
        } catch (IOException e) {
            new MessageBoxCatcher(null).catchException(e);
        }
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    private Locale getLocale() {
        if (locale == null) {
            Properties properties = new Properties();
            try {
                FileInputStream inputStream = new FileInputStream(settingsName);
                properties.load(inputStream);
            } catch (IOException e) {
                new MessageBoxCatcher(null).catchException(e);
            }
            locale = new Locale(properties.getProperty("locale"));
        }

        return locale;
    }
}