package Model;

import ObservListe.Observable;
import ObservListe.Observateur;

/**
 * Tout élément mobile sera gérer sous forme de thread.
 * Un élément mobile peut se déplacer et modifier ses attributs lié à un déplacement.
 */
public abstract class ElementMobile extends Thread implements Observable {

    protected Observateur unObservateur; /** celui qui observe l'élément mobile */

    protected int x; /** Coordonnée polaire */
    protected int y; /** Coordonnée polaire */
    protected int angle; /** angle comme sur un cercle trigonométrique */

    
    public static double convDegGrad = 0.0174533; /** la constante de conversion de degres en gradient  */

    public void avancer(){
        x = (int) Math.round(x + 30*Math.cos(convDegGrad*angle));
        y = (int) Math.round(y + 30*Math.sin(convDegGrad*angle));
    }

    public void droite(){angle = (angle + 90) % 360;}
    public void gauche(){angle = (angle - 90) % 360;}

    /**
     * Chaque fils devra écrire cette méthode
     */
    @Override
    public abstract void run();

    public int getAngle() {return angle;}
    public int getX() {return x;}
    public int getY() {return y;}



}
