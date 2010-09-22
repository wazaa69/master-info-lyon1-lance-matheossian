package tortuesmanager;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    * @param g le graphique (le support de dessin)
    */
    @Override
    protected void dessinerTortue(Graphics g)
    {
        g.drawOval(getX(), getY(), 10,10);
        g.setColor(Color.red);
        g.fillOval(getX(),getY(),10,10);
    }



    /*
     * Recherche la tortue amie la plus proche
     * @return retourne la tortue améliorée amie la plus proche
     */
    public TortueAmelioree tortueAmieLaPlusProche(){

        TortueAmelioree uneTortue = null;
        TortueAmelioree tortueProche = null;

        int distance = 0;
        int distMinimal = Integer.MAX_VALUE; //le maximum d'un integer


        for(int i=0; i < feuille.getListeTortuesAmeliorees().size(); i++){

            uneTortue = feuille.getListeTortuesAmeliorees().get(i);
            distance = distPoint(getX(), getY(), uneTortue.getX(), uneTortue.getY());

            if(distance < distMinimal){
                distMinimal = distPoint(getX(), getY(), uneTortue.getX(), uneTortue.getY());
                tortueProche = uneTortue;
             }
        }
        return tortueProche;
    }



    public void setPositionSelonTortue(Tortue uneTortue){
        setCoordonneesSelonTortue(uneTortue);
        setDir(uneTortue.getDir());
        avancer(15);
    }



    public void setCoordonneesSelonTortue(Tortue uneTortue){
        setX(uneTortue.getX()-5);
        setY(uneTortue.getY()-5);
    }


    /**
     * TODO calcul et dessin à modifier !!! Cette méhode n'est pas utilisée
    * Envoie la balle à une tortue (pas à pas)
    * @param uneTortue cette tortue est la nouvelle propriétaire de la balle.
    */
    
    public void passerLaBalleA(Tortue uneTortue) {

        if(distTortue(uneTortue) != 0){

            //Calcul de la direction
            double cosinus = (uneTortue.getX() - getX() ) / distTortue(uneTortue);
            double angle = Math.toDegrees(cosinus);

            //System.out.println(angle);
            setDir((int) angle);

            //Calcul des coefficients
            int a = (uneTortue.getY()-getY())/(uneTortue.getX()-getX());
            int b = getY() - a * getX();


            int coor_y = getY();

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
    


}