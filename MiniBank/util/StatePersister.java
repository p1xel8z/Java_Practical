package util;

import model.Account;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

public class StatePersister {

    public static void save(Account[] accounts, Path file) throws IOException {
        try (ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(accounts);
        }
    }

    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream(file.toFile()))) {
            return (Account[]) ois.readObject();
        }
    }
}
