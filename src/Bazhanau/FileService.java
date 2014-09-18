package Bazhanau;

import java.io.*;

public class FileService implements IFileService {

    @Override
    public String readText(File file) throws IOException {
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
            throw ex;
        }
        return builder.toString();
    }

    @Override
    public void writeText(File file, String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException ex) {
            throw ex;
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
    public void writeObject(File file, Object object) throws IOException {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(object);
        } catch (IOException ex) {
            throw ex;
        }
    }

    @Override
    public Object readObject(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
            return stream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw ex;
        }
    }
}
