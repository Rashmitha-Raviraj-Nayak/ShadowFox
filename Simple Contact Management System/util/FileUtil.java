package util;

import model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static final String FILE_NAME = "contacts.dat";

    public static void save(List<Contact> contacts) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(contacts);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Contact> load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            return (List<Contact>) ois.readObject();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}