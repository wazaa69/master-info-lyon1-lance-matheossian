package tortuesmanager;

import java.awt.Color;
import java.awt.Graphics;

/*
 * Une balle de jeu
 */
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
    * @param g le support de dessin
    */
    @Override
    protected void dessinerTortue(Graphics g)
    {
        g.drawOval(getX(), getY(), 10,10);
        g.setColor(Color.red);
        g.fillOval(getX(),getY(),10,10);
    }


    /**
     * Met à jour les coordonées et la direction de la balle, selon une tortue
     * @param uneTortue la tortue qui possède la balle
     */
    public void setPositionSelonTortue(Tortue uneTortue){
        setCoordonneesSelonTortue(uneTortue);
        setDir(uneTortue.getDir());
        avancer(15);
    }


    /**
     * Met à jour les coordonées de la balle selon une tortue
     * @param uneTortue la tortue qui possède la balle
     */
    protected void setCoordonneesSelonTortue(Tortue uneTortue){
        setX(uneTortue.getX()-5);
        setY(uneTortue.getY()-5);
    }


    /**
    * Envoie la balle à une tortue (pas à pas) : non utilisée !
    * @param uneTortue cette tortue est la nouvelle propriétaire de la balle.
    */

    /*
    public void passerLaBalleA(Tortue uneTortue) {

        if(distTortue(uneTortue) != 0){

            //Calcul de la direction
            double tan = (uneTortue.getY() - getY()) / ( uneTortue.getX() - getX() );
            double angle = (int) Math.toDegrees(tan); //conversion en degrés

            //System.out.println(angle);
            setDir(angle);

            //Calcul des coefficients
            int a = (uneTortue.getY()-getY())/(uneTortue.getX()-getX());
            int b = getY() - a * getX();

            int coor_y = getY();

            //Déplacement le long de la droite
            for(int coor_x = getX(); coor_y < uneTortue.getY() && coor_x < uneTortue.getX();  coor_x+=10){

                coor_y = a * ( coor_x - getX() ) + b;
                setX(coor_x);
                setY(coor_y);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TortueBalle.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
       }
        setPositionSelonTortue(uneTortue);

        //On met à jour la direction
        setDir(uneTortue.getDir());
    }
    */


}