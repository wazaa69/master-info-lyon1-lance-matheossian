package Model;

import java.awt.Color;

/**
 * Le terrain de jeu
 */
public class Terrain {

    public static int LONGUEUR; /** @param LONGUEUR longueur du terrin*/
    public static int LARGEUR; /** @param LARGEUR largeur du terrin*/

    public final static int BORDUREINTE = 20; /** @param BORDUREINTE bordure intérieur */

    private Color couleur;

    public Terrain(int longueur, int largeur, Color couleur){
        Terrain.LONGUEUR = longueur;
        Terrain.LARGEUR = largeur;
        this.couleur = couleur;
    }

    
}
