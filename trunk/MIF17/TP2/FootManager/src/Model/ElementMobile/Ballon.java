package Model.ElementMobile;

import Model.Terrain.Terrain;
import java.awt.Color;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Ballon extends ElementMobile {

    private int rayon; /** rayon pour la création du ballon */
    private Color couleur; /** la couleur du ballon, pour le différencier sur el terrain */
    private Joueur possesseur; /** le possesseur actuel */
    private Joueur ancienPoss; /** l'ancien pocesseur */


    /**
     * Création et  initialisation du ballon
     * @param x coordonnée en abscisse
     * @param y coordonnée en ordonné
     * @param rayon le rayon du ballon
     * @param couleur sa couleur
     */
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
     * Pour plus tard, si on veut montrer une passe en temps réel
     */
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


/*********************************  METHODE  **********************************/
    /**
     * Place le ballon au centre du terrain
     */
    public void initBallon(){
        setXY(new Point((int) Math.round(Terrain.LONGUEUR/2),(int) Math.round(Terrain.LARGEUR/2)));
        changerDePossesseur(null);
    }

/***************************  GETTER/SETTERS AVANCES **************************/


    /**
     * Changement de possesseur, demande une mise à jour de l'affichage
     * @param nouveauPossesseur le nouveau pocesseur du ballon
     */
    public void changerDePossesseur(Joueur nouveauPossesseur) {

        if(ancienPoss == null) ancienPoss = nouveauPossesseur;

        ancienPoss = possesseur;
        possesseur = nouveauPossesseur;

        if(nouveauPossesseur != null){
            unObservateur.miseAJour();
            majXY();
        }

        try {
            sleep(500); //le temps de voir qui prend le ballon
        } catch (InterruptedException ex) {
            Logger.getLogger(Ballon.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Met à jour les coordonnées du ballon, de façon à le placer devant le joueur
     */
    public void majXY(){

        if(possesseur != null){
            Point coordonnees = coordApresDep(possesseur.getX(), possesseur.getY(), 15, possesseur.getAngle());
            angle = possesseur.getAngle();
            setXY(coordonnees);
        }

    }


/******************************  GETTER/SETTERS  ******************************/

    public Color getCouleur() {return couleur;}

    public int getRayon() {return rayon;}

    public Joueur getPossesseur() {return possesseur;}

    public Joueur getAncienPoss() {return ancienPoss;}



}
