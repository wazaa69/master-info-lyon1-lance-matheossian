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
    private Color couleur;  /** la couleur qui représente l'équipe */


    private ArrayList<Joueur> listeJoueurs; /**  la liste des joueurs */
    private Strategie startegie; /** la stratégie adoptée par l'équipe, neutre par défaut */


    private int score = 0; /** le score actuel de l'équipe, 0 par défaut */
    private Cage nosCage; /** la cage à défendre */


/*******************************  CONSTRUCTEUR  *******************************/

    /**
     * Crée une équipe
     * @param nomEquipe le nom de l'équipe
     * @param couleur la couleur qui représente l'équipe
     * @param nosCage la cage à défendre
     */
    public Equipe(String nomEquipe, Color couleur, Cage nosCage){
        this.nomEquipe = nomEquipe;
        listeJoueurs = new ArrayList<Joueur>();
        startegie = (new StrategieFactory()).creerStrategie(1);
        this.couleur = couleur;
        this.nosCage = nosCage;
    }


/**************************  GETTER/SETTERS évolués  **************************/


    /**
     * Ajoute un joueur à l'équipe
     * @param unJoueur le joueur à ajouter
     */
    public void ajouterUnJoueur(Joueur unJoueur){
        //System.out.println(unJoueur.getNom());
        listeJoueurs.add(unJoueur);
    }

    /**
     * Récupère le nombre de joueurs qui essayent d'intercepter le ballon
     */
    public int getNbIntercepteurs(){

        int nbIntercepteurs = 0;

        for(int i = 0; i < listeJoueurs.size(); i++){
            if(listeJoueurs.get(i).isEnCoursInterc()) nbIntercepteurs++;
        }

        return nbIntercepteurs;
    }


    /**
     * Met à jour le score et notifie la vue pour quelle se mette à jour
     * @param score le nouveau score
     */
     public void setScore(int score) {
        this.score = score;
        unObservateur.miseAJour();
    }

     /**
      * @return retourne le goal de l'équipe
      */
    public JoueurGoal getGoal(){
        if(!listeJoueurs.isEmpty()) return (JoueurGoal) listeJoueurs.get(0);
        return null;
    }

/******************************  GETTER/SETTERS  ******************************/
    


    public ArrayList<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public Color getCouleur() {
        return couleur;
    }

    public int getScore() {
        return score;
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
     * @param startegie une instance d'une classe, qui implémante Strategie
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
