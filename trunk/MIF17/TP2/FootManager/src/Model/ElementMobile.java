package Model;

import ObservListe.Observable;
import ObservListe.Observateur;
import java.awt.Point;

/**
 * Un élément mobile peut se déplacer et modifier ses attributs de déplacement.
 * Tout élément mobile sera gérer sous forme de thread.
 */
public abstract class ElementMobile extends Thread implements Observable {

    protected Observateur unObservateur; /** celui qui observe l'élément mobile */

    protected int x; /**  Coordonnée en abscisse */
    protected int y; /** Coordonnée en ordonnée */
    protected int angle = 0; /**  Schéma :  180° --- | ---- 0°  */

    
    public final static double convDegGrad = 0.0174533; /**  la constante de conversion de degres en gradient  */

    /**  distance minimal entre deux elements mobiles, si elle n'est pas respectée, les deux éléments rentrent en collision */
    protected int distanceMinContact = 25;



/**********************************  THREAD  **********************************/


    /**
     * Le thread qui s'occupe de l'élément mobile.
     * Chaque fils devra écrire cette méthode.
     */
    @Override
    public abstract void run();


/*******************************  DEPLACEMENT  *******************************/


    /**
     * Avance l'élément sur une distance
     * @param distance la distance à parcourir
     */
    public void avancer(int distance){
        x = (int) Math.round(x + distance*Math.cos(convDegGrad*angle));
        y = (int) Math.round(y + distance*Math.sin(convDegGrad*angle));
    }

    /**
     * Rotation à gauche
     */
    public void gauche(){angle = (angle + 90) % 360;}

    /**
     * Rotation à droite
     */
    public void droite(){angle = (angle - 90) % 360;}



/******************************  VERIFICATIONS  ******************************/

    /**
     * Vérifie si la distance entre deux points est respectée
     * @param x coordonnée en abscisse
     * @param y coordonnée en odronnée
     * @param unElemMobile l'élément distant
     * @return retourne vrai si la distance minimal (distance >= distance Minimal) est respectée, faux sinon
     */
    public boolean isBonneDistance(Point unPoint, ElementMobile unElemMobile){
        return (getDistance(unPoint, unElemMobile) >= distanceMinContact);
    }


    /**
     * Calcul de la distance entre deux points (points actuel ---- points distant)
     * @param unElemMobile l'élément distant
     * @return retourne un entier qui indique la distance
     */
    public int getDistance(Point unPoint, ElementMobile unElemMobile) {
        return (int) Point.distance((int) unPoint.getX(),(int) unPoint.getY(), unElemMobile.getX(), unElemMobile.getY());
    }


/******************************  GETTER/SETTERS  ******************************/


    public int getAngle() {return angle;}
    public int getX() {return x;}
    public int getY() {return y;}


/***************************** Méthodes de l'observé **************************/


    public void ajouterObservateur(Observateur obs) {
        unObservateur = obs;
    }

    public void supprimerObservateur() {
        unObservateur = null;
    }

    public void notifierObservateur() {
        unObservateur.miseAJour();
    }

}
