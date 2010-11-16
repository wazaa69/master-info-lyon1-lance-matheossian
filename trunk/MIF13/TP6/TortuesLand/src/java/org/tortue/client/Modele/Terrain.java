package org.tortue.client.Modele;

/**
 * Le terrain
 */
public class Terrain {

    public static int LONGUEUR; /**  longueur du terrain (nb cases en longueur) */
    public static int LARGEUR;  /**  largeur du terrain (nb cases en largeur) */
    public static int MARGEINT; /** la marge intérieur du terrain */

    /**
     * Crée le terrain
     * @param longueur longueur du terrain
     * @param largeur largeur du terrain
     */
    public Terrain(int longueur, int largeur) {
        LONGUEUR = longueur;
        LARGEUR = largeur;
    }

}
