package tortuesmanager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;



public class TortueBalle extends Tortue {


 //######################################################################################################      CONSTRUCTEURS

/**
     * Constructeur pour une TortueBalle, aura un nom par defaut
     * @param f La tortue sera rattache a cette feuille
     */
    public TortueBalle(FeuilleDessin f)
    {
        super(f);

        setCouleur(Color.RED);
    }


 //######################################################################################################      METHODES

    /**
     * Dessine la TortueBalle dans la FeuilleDessin.<br/>
     * On n'appellera pas directement cette fonction, le programme se charge
     * de l'affichage des tortues
     * @param g C'est le contexte graphique de la FeuilleDessin a laquelle
     * la TortueBalle est rattache.
     */


    protected void dessinerTortue(Graphics g)
    {
        Polygon cercle = new Polygon();

        //Rayon du cerle
        double r = 5;

        int n = 16;
        for(int i=0; i<=n; i++)
        {
                Point p = new Point(x+ (int)(r * Math.cos(i*(2*Math.PI/n))),
                                    y+ (int)(r * Math.sin(i*(2*Math.PI/n))));
                cercle.addPoint(p.x, p.y);
        }

        g.setColor(tortueCouleur);
        g.fillPolygon(cercle);

    
    }

}