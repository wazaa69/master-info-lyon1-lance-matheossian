package Model;


import java.awt.Point;

/**
 * Les cage du jeu de foot
 */
public class Cage {

    private Point coordonnees; /** coordonnées de l'angle supérieur gauche */
    private int longueur; /** la longueur de la cage */
    private int largeur; /** la largeur de la cage */

    /**
     * Constructeur : initialise la position et la taille des cages
     * @param xCoinSupG coordonnée en abscisse de l'angle supérieur gauche de la cage
     * @param yCoinSupG coordonnée en ordonné de l'angle supérieur gauche de la cage
     */
    public Cage(int xCoinSupG, int yCoinSupG) {

        coordonnees = new Point(xCoinSupG, yCoinSupG);

        largeur = Terrain.MARGESEINTE;
        longueur = 100; //atttention, on part de l'angle sup gauche, donc 2*50
    }

    public Point getCoordonnees() {
        return coordonnees;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getLongueur() {
        return longueur;
    }


}
