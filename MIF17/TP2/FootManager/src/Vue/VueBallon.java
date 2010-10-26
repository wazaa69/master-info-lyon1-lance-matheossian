package Vue;

import Model.ElementMobile.Ballon;
import java.awt.Graphics;

/**
 * Représentation dun ballon pour l'affichage
 */
public class VueBallon extends VueElemMobiles {

    Ballon unBallon;  /** le ballon du model */

/*******************************  CONSTRUCTEUR  *******************************/

    public VueBallon(Ballon unBallon) {
        this.unBallon = unBallon;
    }

/**********************************  DESSIN  **********************************/

    /**
     * Dessine le ballon
     * @param g le contexte graphique
     */
    public void dessiner(Graphics g, Boolean clear){
        g.setColor(unBallon.getCouleur());
        g.fillOval(unBallon.getX()-5, unBallon.getY()-5, unBallon.getRayon()*2, unBallon.getRayon()*2);
    }

}
