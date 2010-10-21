package Model;

import java.awt.Color;
import java.awt.Dimension;

/**
 * Le terrain de jeu
 */
public class Terrain {

    private Color couleur; /** couleur du terrain */

    public final static int MARGESEINTE = 10; /**  marge intérieur */
    public static int LONGUEUR; /**   longueur du terrin*/
    public static int LARGEUR; /**  largeur du terrin*/

    
    private Cage cageGauche; /** cage à gauche du terrain */
    private Cage cageDroite; /** cage à droite du terrain */


    public Terrain(int longueur, int largeur, Color couleur){

        //Mise à jour de dimenssions
        Terrain.LONGUEUR = longueur;
        Terrain.LARGEUR = largeur;

        //Création des cages
        cageGauche = new Cage(0, (Terrain.LARGEUR/2) - 50);
        cageDroite = new Cage(Terrain.LONGUEUR - Terrain.MARGESEINTE, (Terrain.LARGEUR/2) - 50);

        this.couleur = couleur;
    }

    /**
     * Calcul de la taille du terrain en supprimant les marges intérieurs
     * @return retourne une dimenssion
     */
    public static Dimension getDimTerrain(){
        return new Dimension(LONGUEUR - 2*MARGESEINTE, LARGEUR - 2*MARGESEINTE);
    }

    public Cage getCageGauche() {
        return cageGauche;
    }

    public Cage getCageDroite() {
        return cageDroite;
    }

}