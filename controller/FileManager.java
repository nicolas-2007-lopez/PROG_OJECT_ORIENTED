package controller;

import java.io.*;
import java.util.List;
import model.User;
import model.interfaces.Tuit;

public class FileManager {

    private static final String USER_FILE = "data/user.dat";
    private static final String FEED_FILE = "data/feed.dat";

    public static void saveUser(User user) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            out.writeObject(user);
            System.out.println("✅ Usuario guardado.");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar el usuario.");
            e.printStackTrace();
        }
    }

    public static User loadUser() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            return (User) in.readObject();
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo cargar el usuario. Se creará uno nuevo.");
            return null;
        }
    }

    public static void saveFeed(List<Tuit> tuits) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FEED_FILE))) {
            out.writeObject(tuits);
            System.out.println("✅ Feed guardado.");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar el feed.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Tuit> loadFeed() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FEED_FILE))) {
            return (List<Tuit>) in.readObject();
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo cargar el feed. Se empezará vacío.");
            return null;
        }
    }
    public static void saveFeedForUser(String email, List<Tuit> tuits) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/feed_" + email + ".dat"))) {
        out.writeObject(tuits);
        System.out.println("✅ Feed guardado para: " + email);
    } catch (IOException e) {
        System.out.println("❌ Error al guardar el feed de " + email);
        e.printStackTrace();
    }
}

@SuppressWarnings("unchecked")
public static List<Tuit> loadFeedForUser(String email) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/feed_" + email + ".dat"))) {
        return (List<Tuit>) in.readObject();
    } catch (Exception e) {
        System.out.println("⚠️ No se encontró feed de " + email + ". Se empezará vacío.");
        return null;
    }
}

}
