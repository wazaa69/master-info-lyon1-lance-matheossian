package org.tortue.client.Modele;

import java.util.ArrayList;

/**
 * Le client et ses tortues
 */
public class Client {

    private String nom;

    private ArrayList<Tortue> listeTortues = new ArrayList<Tortue>();


    public Client(String nom) {
        this.nom = nom;
    }

    public void addTortue(String nom){
        listeTortues.add(new Tortue(nom, listeTortues.size(), 0, 0));
    }

    public Tortue getTortue(int id){
        return listeTortues.get(id);
    }

    public void deleteTortue(int id){
        listeTortues.remove(id);
    }

    public String getNom() {
        return nom;
    }
    
}
