package Model.ElementMobile;

import Model.Equipe;
import Model.Terrain.Terrain;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Le joueur représentant le goal, il pourra se déplacer uniquement sur un axe.
 */
public class JoueurGoal extends Joueur {

    int yMin; /** valeur minimal en ordonné pour rester devant les cages */
    int yMax;  /** valeur maximal en ordonné pour rester devant les cages */


    
/*******************************  CONSTRUCTEUR  *******************************/

    public JoueurGoal(String nom, Ballon ballonDuJeu, Equipe monEquipe, Equipe equipeAdverse) {

        //appel du constructeur parent, notamment pour ajouter les équipes
        super(nom, ballonDuJeu, monEquipe, equipeAdverse);

        //positionnement du goal au milieu de ses cages
        //on différencie les deux cas, si le goal est dans la cage de gauche ou de droite
        int xCage = (int) monEquipe.getCage().getCoordonnees().getX();

        //si l'angle supérieur gauche de la cage est à gauche
        if(xCage < Terrain.LARGEUR/2){
            x = xCage + monEquipe.getCage().getLargeur();
            angle = getAngleSelonBallon(); //goal de gauche
        }
        
        else {
            x = xCage; //abscisse de l'angle supérieur gauche de la cage
            angle = 180 + getAngleSelonBallon(); //goal de droite
        }

        y = (int) monEquipe.getCage().getCoordonnees().getY() + (monEquipe.getCage().getLongueur()/2);


        //modification de la distance d'interception du gardien en fonction de la longueur des cages
        caracteristiques.setDistMinPrendreBalle(monEquipe.getCage().getLongueur()/5);
        caracteristiques.setDistDep(5);

        //Délimitation de sa zone de déplacement
        yMin = (int) monEquipe.getCage().getCoordonnees().getY();
        yMax = yMin + monEquipe.getCage().getLongueur();

    }

/****************************** BOUCLE DE THREAD ******************************/

    @Override
    public void demarrerJoueur() {

        
        while(true){

            angle = getAngleSelonBallon();

            int tmpY = y;

            //nouveaux y
            if(Math.random() >  0.5)
                tmpY += caracteristiques.getDistDep();
            else
                tmpY -= caracteristiques.getDistDep();

            //minoration, majoration
            if(tmpY >= yMax)
                tmpY = yMax;
            else if (tmpY <= yMin)
                tmpY = yMin;

            //validité
            if(isValideDistContact(new Point(x,tmpY)))
                y = tmpY;
            

            try {
                sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }



}