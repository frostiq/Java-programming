package Bazhanau;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileService implements IFileService {

    public String readFile(File file) {
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

    public void writeText(File file, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (Exception ex) {
            Catcher.catchException(ex);
        }
    }

    public String encodeText(String text, String key) {
        char[] charText = text.toCharArray();
        char[] charKey = key.toCharArray();
        for (int i = 0; i < charText.length; i++) {
            charText[i] ^= charKey[i % charKey.length];
        }
        return String.copyValueOf(charText);
    }
}
