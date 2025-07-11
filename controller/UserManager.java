package controller;

import model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final String USERS_FILE = "data/users.dat";
    private List<User> usuarios;

    public UserManager() {
        usuarios = cargarUsuarios();
    }

    
    public User buscarUsuario(String correo) {
        for (User u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(correo)) {
                return u;
            }
        }
        return null;
    }

    
    public User registrarUsuario(String nombre, String correo, String clave, int edad, String foto) {
    User nuevo = new User(nombre, correo, clave, edad, foto);
    usuarios.add(nuevo);
    guardarUsuarios();
    return nuevo;
}


   public void guardarUsuarios() {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
        out.writeObject(usuarios);
        System.out.println("✅ Usuarios guardados.");
    } catch (IOException e) {
        System.out.println("❌ Error al guardar usuarios.");
        e.printStackTrace();
    }
}


    @SuppressWarnings("unchecked")
    public List<User> cargarUsuarios() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            return (List<User>) in.readObject();
        } catch (Exception e) {
            System.out.println("⚠️ No se encontraron usuarios guardados. Se creará lista vacía.");
            return new ArrayList<>();
        }
    }
}
