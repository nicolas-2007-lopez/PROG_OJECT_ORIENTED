package controller;

import model.*;
import model.interfaces.Tuit;

import java.util.List;
import controller.FileManager;
import java.util.ArrayList;

public class AppController {
    private User currentUser;
    private Feed miFeed;
    private List<Tuit> feedGlobal;
    private UserManager userManager;

public void guardarDatos() {
    FileManager.saveUser(currentUser);
    FileManager.saveFeed(feedGlobal);
    FileManager.saveFeedForUser(currentUser.getEmail(), miFeed.getTuits());
}
public void postTuit(String content, String media, String link) {
    Post nuevoPost = currentUser.createPost(content, media, link);
    miFeed.addTuit(nuevoPost);
    feedGlobal.add(nuevoPost);
    System.out.println("📢 Tuit publicado por " + currentUser.getName());
}
public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
}

public UserManager getUserManager() {
    return userManager;
}


public void replyToTuit(Tuit tuit, String replyContent) {
    Reply reply = new Reply(replyContent, currentUser, tuit);
    miFeed.addTuit(reply);
    feedGlobal.add(reply);
    System.out.println("💬 Respuesta creada a: " + tuit.getContent());
}


public List<Tuit> getFeedDelUsuario() {
    String correo = currentUser.getEmail();
    List<Tuit> propios = new ArrayList<>();
    for (Tuit t : feedGlobal) {
        if (t.getAuthor().getEmail().equalsIgnoreCase(correo)) {
            propios.add(t);
        }
    }
    return propios;
}


    public List<Tuit> getFeedGlobal() {
    return feedGlobal;
}

    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User user) {
    this.currentUser = user;

    miFeed = new Feed(user);
    List<Tuit> personales = FileManager.loadFeedForUser(user.getEmail());
    if (personales != null) {
        for (Tuit t : personales) {
            miFeed.addTuit(t);
        }
    }

    feedGlobal = FileManager.loadFeed();
    if (feedGlobal == null) {
        feedGlobal = new ArrayList<>();
    } else {
    feedGlobal.sort((a, b) -> b.getDate().compareTo(a.getDate()));
}
   } 
public void eliminarTuit(Tuit tuit) {
    miFeed.getTuits().remove(tuit);
    feedGlobal.remove(tuit);
    guardarDatos(); 
    System.out.println("🗑️ Tuit eliminado de memoria y archivos.");
}




} 

