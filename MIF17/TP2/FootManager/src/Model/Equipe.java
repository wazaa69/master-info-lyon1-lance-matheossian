package Model;

import Model.Terrain.Cage;
import Model.ElementMobile.Joueur;
import Model.ElementMobile.JoueurGoal;
import Model.Strategies.Strategie;
import Model.Strategies.StrategieFactory;
import ObservListe.Observeur;
import java.awt.Color;
import java.util.ArrayList;


/**
 * Un équipe avec une stratégie et composée de plusieurs joueurs
 */
public class Equipe implements ObservListe.Observable {

    Observeur unObservateur;

    private String nomEquipe; /** le nom de l'équipe */
    private Color couleur;  /** couleur la couleur qui représente l'équipe */


    private ArrayList<Joueur> listeJoueurs; /**  listeJoueurs la liste des joueurs */
    private Strategie startegie = (new StrategieFactory()).creerStrategie(0); /** la stratégie adoptée par l'équipe, attaque par défaut */


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
        unObservateur.miseAJour();
    }

    /**
     * Attention, Strategie est une interface. Cette méthode récupère la stratégie de l'équipe
     * @return retourne une instance d'une classe, qui implémante Strategie
     */
    public Strategie getStartegie() {
        return startegie;
    }

    /**
     * Met à jour la strategie de l'équipe
     * @param une instance d'une classe, qui implémante Strategie
     */
    public void setStartegie(Strategie startegie) {
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

    public JoueurGoal getGoal(){
        if(!listeJoueurs.isEmpty()) return (JoueurGoal) listeJoueurs.get(0);
        return null;
    }


/***************************** Méthodes de l'observé **************************/


    public void ajouterObserveur(Observeur obs) {
        unObservateur = obs;
    }

    public void supprimerObserveur() {
        unObservateur = null;
    }

    public void notifierObserveur() {
        unObservateur.miseAJour();
    }
}
