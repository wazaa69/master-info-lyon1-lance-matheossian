package tortuesmanager;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Gestion des équipes
 */
public class Equipe{

    private int couleur;
    private ArrayList<TortueAmelioree> listeTortues;

    public void Equipe(int couleur){
        this.couleur = couleur;
        this.listeTortues = listeTortues;
    }


     /**
     * Retourne la ième tortue
     * @param i la position de la tortue dans la liste
     * @return La tortue à la position spécifiée en argument
     */
    Tortue getTurtle(int i) {return listeTortues.get(i);}

    /**
     * Insert une tortue dans l'équipe
     * @param tortue la tortue à ajouter
     */
    void insertTurtle (TortueAmelioree tortue){listeTortues.add(tortue);}

    /**
     * Retire une tortue de l'équipe
     * @param tortue la tortue a enlever
     * @return vrai si la tortue a été retirée, faux sinon
     */
    boolean removeTurtle(Tortue tortue) {return listeTortues.remove(tortue);}

    /**
     * Retourne le nombre de Tortue dans l'équipe
     * @return le nombre de Tortue dans l'équipe
     */
    int size(){return listeTortues.size();}


    /*-----------------------------------*/
    /*         GETTERS/SETTERS
    /*-----------------------------------*/

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public ArrayList<TortueAmelioree> getListeTortues() {
        return listeTortues;
    }

    public void setListeTortues(ArrayList<TortueAmelioree> listeTortues) {
        this.listeTortues = listeTortues;
    }

}
