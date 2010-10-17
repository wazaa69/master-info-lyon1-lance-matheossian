package Model;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Un équipe avec une stratégie et composée de plusieurs joueurs
 */
public class Equipe {

    private ArrayList<Joueur> listeJoueurs; /**  listeJoueurs la liste des joueurs */

    private Color couleur;  /**  couleur la couleur qui représente l'équipe */

    private int score = 0; /**  le score actuel de l'équipe */

    
    public Equipe(Color couleur){
        listeJoueurs = new ArrayList<Joueur>();
        this.couleur = couleur;       
    }

    public Equipe(Color couleur, ArrayList<Joueur> listeJoueurs) {
        this.listeJoueurs = listeJoueurs;
        this.couleur = couleur;
    }


    

/******************************  GETTER/SETTERS  ******************************/
    
    public void ajouterUnJoueur(Joueur unJoueur){
        //System.out.println(unJoueur.getNom());
        listeJoueurs.add(unJoueur);
    }

    public ArrayList<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public Color getCouleur() {
        return couleur;
    }

    
}
