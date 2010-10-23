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
    protected int angle; /**  Schéma :  180° --- | ---- 0°  */

    /**  distance minimal entre deux elements mobiles, si elle n'est pas respectée, les deux éléments rentrent en collision */
    protected int distanceMinContact;


    public ElementMobile() {
        x = 0;
        y = 0;
        angle = 0;
        distanceMinContact = 25;
    }
  
/**********************************  THREAD  **********************************/


    /**
     * Le thread qui s'occupe de l'élément mobile.
     * Chaque fils devra écrire cette méthode.
     */
    @Override
    public abstract void run();


/*******************************  DEPLACEMENT  *******************************/

    /**
     * Calcul des nouvelles coordonnées selon un point de départ, une distance et un angle
     * @param x point de départ en abscisse
     * @param y point de départ en ordonnée
     * @param distDep une vitesse de déplacement
     * @param angle l'angle
     * @return retourne la position du nouveau point
     */
    protected  Point coordApresDep(int x, int y, int distDep, int angle){

        Point point = new Point();

        point.setLocation(
                Math.round(x + distDep * Math.cos(Math.toRadians(angle))),
                Math.round(y + distDep * Math.sin(Math.toRadians(angle)))
                );

        return point;
    }

    /**
     * Avance l'élément sur une distance
     * @param distance la distance à parcourir
     */
    public void avancer(int distDep){
        x = (int) Math.round(x + distDep*Math.cos(Math.toRadians(angle)));
        y = (int) Math.round(y + distDep*Math.sin(Math.toRadians(angle)));
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

    public void setXY(Point unPoint) {
         x = (int) unPoint.getX();
         y = (int) unPoint.getY();
    }

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
