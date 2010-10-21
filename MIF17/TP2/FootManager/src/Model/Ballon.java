package Model;

import java.awt.Color;


public class Ballon extends ElementMobile {

    private Color couleur; /**  couleur la couleur de la balle */
    private Joueur possesseur; /**  le possesseur de la balle */


    public Ballon(int x, int y, Color couleur) {

        this.x = x +100 ;
        this.y = y + 100;

        this.couleur = couleur;
        this.possesseur = null;
    }
    
    /**
     * Pour plus tard si on veut montrer une passe
     */
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    
    public Color getCouleur() {
        return couleur;
    }

    public Joueur getPossesseur() {
        return possesseur;
    }
    
}
