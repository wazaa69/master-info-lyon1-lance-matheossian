package Model;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Un équipe avec une stratégie et composée de plusieurs joueurs
 */
public class Equipe {

    private String nomEquipe; /** le nom de l'équipe */
    private Color couleur;  /** couleur la couleur qui représente l'équipe */


    private ArrayList<Joueur> listeJoueurs; /**  listeJoueurs la liste des joueurs */
    private int startegie = 0; /** la stratégie adoptée par l'équipe, neutre par défaut */


    private int score = 0; /** le score actuel de l'équipe, 0 par défaut */
    private Cage nosCage; /** les cages de l'équipe*/


/*******************************  CONSTRUCTEUR  *******************************/

    public Equipe(String nomEquipe, Color couleur, Cage nosCage){
        this.nomEquipe = nomEquipe;
        listeJoueurs = new ArrayList<Joueur>();
        this.couleur = couleur;
        this.nosCage = nosCage;
    }

    public Equipe(String nomEquipe,Color couleur, ArrayList<Joueur> listeJoueurs, Cage nosCage) {
        this.listeJoueurs = listeJoueurs;
        this.couleur = couleur;
        this.nosCage = nosCage;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStartegie() {
        return startegie;
    }

    public void setStartegie(int startegie) {
        this.startegie = startegie;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public Cage getCage() {
        return nosCage;
    }

    public void setCage(Cage cage) {
        this.nosCage = cage;
    }

    
  
}
