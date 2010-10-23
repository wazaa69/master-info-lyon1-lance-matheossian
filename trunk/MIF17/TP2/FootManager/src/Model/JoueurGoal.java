package Model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Le joueur représentant le goal, il pourra se déplacer uniquement sur un axe.
 */
public class JoueurGoal extends Joueur {

    int yMin; /** valeur minimal en ordonné pour rester devant les cages */
    int yMax;  /** valeur maximal en ordonné pour rester devant les cages */


    
/*******************************  CONSTRUCTEUR  *******************************/

    public JoueurGoal(String nom, Equipe monEquipe, Equipe equipeAdverse) {

        //appel du constructeur parent, notamment pour ajouter les équipes
        super(nom, monEquipe, equipeAdverse);

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


        //Délimitation de sa zone de déplacement
        yMin = (int) monEquipe.getCage().getCoordonnees().getY();
        yMax = yMin + monEquipe.getCage().getLongueur();

    }

/****************************** BOUCLE DE THREAD ******************************/

    @Override
    public void demarrerJoueur() {

        
        while(true){
            angle = getAngleSelonBallon();

            try {
                sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }



}