package Model.ElementMobile;

import java.awt.Color;
import java.awt.Point;


public class Ballon extends ElementMobile {

    private int rayon;
    private Color couleur;
    private Joueur possesseur;
    private Joueur ancienPoss; /** l'ancien pocesseur */


    public Ballon(int x, int y, int rayon, Color couleur) {

        this.rayon = rayon;

        this.x = x;
        this.y = y;

        this.couleur = couleur;
        this.possesseur = null;
        this.ancienPoss = null;

    }

/**********************************  THREAD ***********************************/

    /**
     * Pour plus tard si on veut montrer une passe
     */
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



/******************************  GETTER/SETTERS  ******************************/

    public Color getCouleur() {
        return couleur;
    }

    public int getRayon() {
        return rayon;
    }


    public Joueur getPossesseur() {
        return possesseur;
    }

    /**
     * Changement de pocesseur
     * @param possesseur le nouveau pocesseur du ballon
     */
    public void setPossesseur(Joueur possesseur) {
        this.possesseur = possesseur;
        majXY();
    }

    public void setAncienPoss(Joueur ancienPoss) {
        this.ancienPoss = ancienPoss;
    }

    public Joueur getAncienPoss() {
        return ancienPoss;
    }



    /**
     * Met à jour les coordonnées du ballon, de façon à le placer devant le joueur
     */
    public void majXY(){

        Point coordonnees = coordApresDep(possesseur.getX(), possesseur.getY(), 15, possesseur.getAngle());
        setXY(coordonnees);
        angle = possesseur.getAngle();
    }


}
