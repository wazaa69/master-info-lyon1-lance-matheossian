package Vue;

import Model.Joueur;
import ObservListe.Observateur;
import java.awt.Graphics;

import java.awt.Point;
import java.awt.Polygon;

/**
 * La classe qui représente visuellement un joueur
 */
class VueJoueur {

    VueTerrain vueTerrain; /** cette vue connait son conteneur parent */

    Joueur unJoueur; /** la vue à une référence sur le model */

    protected static final int rp = 10, rb = 5; /** pour le tracé des joueurs  */

    /**
     * Les coordonnées du model serviront à afficher le joueur aux bonnes coordonnées polaires
     * @param unJoueur une joueur du model
     */
    public VueJoueur(VueTerrain vueTerrain, Joueur unJoueur) {

        this.vueTerrain = vueTerrain;

        this.unJoueur = unJoueur;
        
        this.unJoueur.ajouterObservateur(new Observateur() {

            public void miseAJour() {afficher();}

        } );
    }

    /**
     * Dessine le joueur
     */
    public void afficher(){
        
        //System.out.println("Joueur " + unJoueur.getNom() + " dessiné.");

        Graphics g = vueTerrain.getImageGraphics();

        
        //Calcule les 3 coins du triangle a partir de la position du joueur
        Point pointUn = new Point(unJoueur.getX(),unJoueur.getY());
        Polygon arrow = new Polygon();


        //Calcule des deux bases

        //Angle de la droite
        double theta = Joueur.convDegGrad * (- unJoueur.getAngle());
        //Demi angle au sommet du triangle
        double alpha = Math.atan( (float) rb / (float)rp );
        //Rayon de la fleche
        double r = Math.sqrt( rp*rp + rb*rb );
        //Sens de la fleche

        //Pointe
        Point pointDeux = new Point((int) Math.round(pointUn.getX()+r*Math.cos(theta)),
                             (int) Math.round(pointUn.y-r*Math.sin(theta)));

        arrow.addPoint(pointDeux.x,pointDeux.y);

        arrow.addPoint((int) Math.round( pointDeux.getX()-r*Math.cos(theta + alpha) ),
                       (int) Math.round( pointDeux.y+r*Math.sin(theta + alpha) ));

        //Base2
        arrow.addPoint((int) Math.round( pointDeux.x-r*Math.cos(theta - alpha) ),
                       (int) Math.round( pointDeux.y+r*Math.sin(theta - alpha) ));

        arrow.addPoint(pointDeux.x,pointDeux.y);


        
        //on accède au modèle pour récupérer la couleur de l'équipe
        g.setColor(unJoueur.getMonEquipe().getCouleur());
        
        g.fillPolygon(arrow);

        //on rafraichi le conteneur de cette tortue
        vueTerrain.updateUI();

    }

}
