package org.tortue.client.Modele;

/**
 * Le terrain
 */
public class Terrain {

    private int longueur; /**  longueur du terrain (nb cases en longueur) */
    private int largeur;  /**  largeur du terrain (nb cases en largeur) */
    private int margint; /** la marge intérieur du terrain */

    /**
     * Crée le terrain
     * @param longueur longueur du terrain
     * @param largeur largeur du terrain
     */
    public Terrain(int longueur, int largeur) {
        this.longueur = longueur;
        this.largeur = largeur;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getMargint() {
        return margint;
    }



}
