package org.tortue.server;

import java.util.ArrayList;
import org.tortue.client.Modele.Tortue;

/**
 * Le client et ses tortues
 */
public class Client {

    private String nom;

    private ArrayList<Tortue> listeTortues = new ArrayList<Tortue>();


    public Client(String nom) {
        this.nom = nom;
    }

    public void addTortue(String nomTortue){
        listeTortues.add(new Tortue(nomTortue, listeTortues.size()+1, 0, 0));
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
