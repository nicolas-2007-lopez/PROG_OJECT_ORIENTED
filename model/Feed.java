package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.interfaces.Tuit;
import java.io.Serializable;

public class Feed implements Serializable {
    private User owner;
    private List<Tuit> tuits;

    public Feed(User owner) {
        this.owner = owner;
        this.tuits = new ArrayList<>();
    }

    public void addTuit(Tuit tuit) {
        tuits.add(tuit);
    }

public void sortByDate() { 
    System.out.println("🟨 Verificando fechas antes de ordenar el feed:");

    for (Tuit t : tuits) {
        System.out.println("Autor: " + 
            (t.getAuthor() != null ? t.getAuthor().getName() : "") + 
            " | Fecha: " + 
            (t.getDate() != null ? t.getDate().toString() : ""));
    }

    Collections.sort(tuits, new Comparator<Tuit>() {
        public int compare(Tuit t1, Tuit t2) {
            return t2.getDate().compareTo(t1.getDate()); 
        }
    });
}


    public void sortByRelevance() {
        Collections.sort(tuits, new Comparator<Tuit>() {
            public int compare(Tuit t1, Tuit t2) {
                return t2.getLikes() - t1.getLikes(); 
            }
        });
    }

    public void display() {
        for (Tuit tuit : tuits) {
            System.out.println(tuit.getAuthor().getName() + ": " + tuit.getPreview());
        }
    }

    public List<Tuit> getTuits() {
        return tuits;
    }
    public void eliminarTuit(Tuit tuit) {
    tuits.remove(tuit);
}
}
