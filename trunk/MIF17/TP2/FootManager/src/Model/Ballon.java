package Model;

import java.awt.Color;
import java.awt.Point;


public class Ballon extends ElementMobile {

    private Color couleur;
    private Joueur possesseur;
    private Joueur ancienPoss; /** l'ancien pocesseur */


    public Ballon(int x, int y, Color couleur) {

        this.x = x;
        this.y = y;

        this.couleur = couleur;
        this.possesseur = null;

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

    /**
     * Met à jour les coordonnées du ballon, de façon à le placer devant le joueur
     */
    public void majXY(){

        Point coordonnees = coordApresDep(possesseur.getX(), possesseur.getY(), 15, possesseur.getAngle());
        setXY(coordonnees);
        angle = possesseur.getAngle();
    }


}
