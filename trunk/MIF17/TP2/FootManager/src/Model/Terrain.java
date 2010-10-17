package Model;

import java.awt.Color;
import java.awt.Dimension;

/**
 * Le terrain de jeu
 */
public class Terrain {

    public static int LONGUEUR; /**   longueur du terrin*/
    public static int LARGEUR; /**  largeur du terrin*/

    public final static int MARGESEINTE = 10; /**  marge intérieur */

    private Color couleur;

    public Terrain(int longueur, int largeur, Color couleur){
        Terrain.LONGUEUR = longueur;
        Terrain.LARGEUR = largeur;
        this.couleur = couleur;
    }

    /**
     * Calcul de la taille du terrain en supprimant les marges intérieurs
     * @return retourne une dimenssion
     */
    public static Dimension getDimTerrain(){
        return new Dimension(LONGUEUR - 2*MARGESEINTE, LARGEUR - 2*MARGESEINTE);
        
    }
    
}
