package Bazhanau;

import java.io.*;

public class FileService implements IFileService {

    @Override
    public String readText(File file) {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char['?'];
        try {
            try (FileReader reader = new FileReader(file)) {
                int count;
                while ((count = reader.read(buffer)) != -1) {
                    builder.append(buffer, 0, count);
                }
            }
        } catch (Exception ex) {
            Catcher.catchException(ex);
        }
        return builder.toString();
    }

    @Override
    public void writeText(File file, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (Exception ex) {
            Catcher.catchException(ex);
        }
    }

    @Override
    public String encodeText(String text, String key) {
        char[] charText = text.toCharArray();
        char[] charKey = key.toCharArray();
        for (int i = 0; i < charText.length; i++) {
            charText[i] ^= charKey[i % charKey.length];
        }
        return String.copyValueOf(charText);
    }

    @Override
    public void writeObject(File file, Object object) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(object);
        } catch (Exception ex) {
            Catcher.catchException(ex);
        }
    }

    @Override
    public Object readObject(File file) {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
            return stream.readObject();
        } catch (Exception ex) {
            Catcher.catchException(ex);
        }
        return null;
    }
}
