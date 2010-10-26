package Vue;

import Model.ElementMobile.Joueur;
import java.awt.Graphics;

import java.awt.Point;
import java.awt.Polygon;

/**
 * La classe qui représente visuellement un joueur
 * TODO :
 * un joueur doit pouvoir noifier sa VueJoueur, et VuJoueur doit mettre à jour son affichage (et non VueTerrain)
 * -> déjà testé mais les joueurs ne s'affichaient pas. Voir pour plus tard.
 */
class VueJoueur extends VueElemMobiles {

    Joueur unJoueur; /** la vue à une référence sur le model */

    protected final Integer hauteur = 10; /** hauteur du triangle isocel */
    protected final Integer largeurBase = 5; /** largeurBase de la base du triangle isocel */


/*******************************  CONSTRUCTEUR  *******************************/

    /**
     * Les coordonnées du model serviront à dessiner le joueur
     * @param unJoueur une joueur du model
     */
    public VueJoueur(Joueur unJoueur) {
        this.unJoueur = unJoueur;
    }


/**********************************  DESSIN  **********************************/

    /**
     * Dessine le joueur
     */
    public void dessiner(Graphics g, Boolean clear){
        
        //System.out.println("Joueur " + unJoueur.getNom() + " dessiné.");

        //on récupère les coordonnées du joueur
        Point pointUn = new Point(unJoueur.getX(),unJoueur.getY());

        //Calcule les 3 coins du triangle a partir de la position du joueur

        //création d'un nouveau polygon
        Polygon fleche = new Polygon();

        //Calcule des deux bases

        //Angle de la droite
        double theta = - Math.toRadians(unJoueur.getAngle());
        //Demi angle au sommet du triangle
        double alpha = Math.atan( (float) largeurBase / (float)hauteur );
        //Rayon de la fleche
        double rayon = Math.sqrt( hauteur*hauteur + largeurBase*largeurBase );
        //Sens de la fleche

        //Pointe
        Point pointDeux = new Point((int) Math.round(pointUn.getX() + rayon * Math.cos(theta)),
                                    (int) Math.round(pointUn.getY() - rayon * Math.sin(theta)));

        fleche.addPoint(pointDeux.x,pointDeux.y);

        fleche.addPoint((int) Math.round( pointDeux.getX()- rayon * Math.cos(theta + alpha) ),
                        (int) Math.round( pointDeux.getY()+ rayon * Math.sin(theta + alpha) ));

        //Base2
        fleche.addPoint((int) Math.round(pointDeux.getX() - rayon * Math.cos(theta - alpha) ),
                       (int) Math.round( pointDeux.getY() + rayon * Math.sin(theta - alpha) ));

        fleche.addPoint((int) pointDeux.getX(),(int) pointDeux.getY());

        g.setColor(unJoueur.getMonEquipe().getCouleur());

        g.fillPolygon(fleche);

    }



}
