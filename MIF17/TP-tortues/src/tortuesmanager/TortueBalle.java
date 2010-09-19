package tortuesmanager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;



public class TortueBalle extends Tortue {


 //######################################################################################################      CONSTRUCTEURS

    /**
    * Constructeur, crée la balle et lui attribut une couleur
    * @param feuille la feuille de dessin
    */
    public TortueBalle(FeuilleDessin feuille)
    {
        super(feuille, false);
        setCouleur(Color.RED);
    }


 //######################################################################################################      METHODES

    /**
    * Dessine la TortueBalle dans la FeuilleDessin.
    * @param g le graphique (le support de dessin)
    */
    protected void dessinerTortue(Graphics g)
    {

        double r = 300; //Rayon du cerle
        int n = 16; //Détails du contour de la balle

        //Cercle = polygaone à n sommets
        Polygon cercle = new Polygon();


        //On boucle pour faire un cercle
        for(int i=0; i<=n; i++)
        {
                Point p = new Point(getX()+ (int)(r * Math.cos(i*(2*Math.PI/n))),
                                    getY()+ (int)(r * Math.sin(i*(2*Math.PI/n))));
                cercle.addPoint(p.x, p.y);
        }

        g.setColor(tortueCouleur);
        g.fillPolygon(cercle);
    }



    /*
     * Recherche la tortue la plus proche
     * @return retourne la tortue la plus proche
     */
    public TortueAmelioree tortueLaPlusProche(){

        TortueAmelioree uneTortue = null;
        ArrayList<TortueAmelioree> listeAmies = feuille.getListeTortuesAmeliorees();

        if(!listeAmies.isEmpty()){

            //Initialisation
            TortueAmelioree tortueProche = listeAmies.get(0);
            int dist = distPoint(getX(), getY(), tortueProche.getX(), tortueProche.getY());

            for(int i=1; i < listeAmies.size(); i++){

                uneTortue = listeAmies.get(i);

                if(distPoint(getX(), getY(), uneTortue.getX(), uneTortue.getY()) < dist){
                    dist = distPoint(getX(), getY(), uneTortue.getX(), uneTortue.getY());
                    tortueProche = uneTortue;
                 }
            }

            return tortueProche;
        }

        else return null;

    }



    /**
    * Modifie la position et la direction de la balle en fonction d'une Tortue.
    * @param uneTortue cette tortue est la nouvelle propriétaire de la balle.
    */
    public void setNouvelleProprio(Tortue uneTortue){
        setX(uneTortue.getX());
        setY(uneTortue.getY());
        setDir(uneTortue.getDir());
    }


}