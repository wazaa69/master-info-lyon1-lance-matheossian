package Model;

import java.awt.Color;
import java.util.ArrayList;

public class Equipe {

    private ArrayList<Joueur> listeJoueurs;

    private Color couleur;

    public Equipe(Color couleur){
        listeJoueurs = new ArrayList<Joueur>();
        this.couleur = couleur;       
    }

    public Equipe(Color couleur, ArrayList<Joueur> listeJoueurs) {
        this.listeJoueurs = listeJoueurs;
        this.couleur = couleur;
    }

    public void ajouterUnJoueur(Joueur unJoueur){
//        System.out.println(unJoueur.getNom());
        listeJoueurs.add(unJoueur);
    }

}
