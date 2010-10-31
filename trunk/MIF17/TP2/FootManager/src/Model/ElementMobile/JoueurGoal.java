package Model.ElementMobile;

import Model.Equipe;
import Model.Terrain.Terrain;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Le joueur représentant le goal, il pourra se déplacer uniquement sur l'axe des ordonnés.
 */
public class JoueurGoal extends Joueur {

    int yMin; /** valeur minimal en ordonné pour rester devant les cages */
    int yMax;  /** valeur maximal en ordonné pour rester devant les cages */


    
/*******************************  CONSTRUCTEUR  *******************************/

    /**
     * Initialise et placeme le Goal selon son équipe
     * @param nom son nom
     * @param ballonDuJeu le ballon du jeu de foot
     * @param monEquipe son équipe
     * @param equipeAdverse l'équipe adverse
     */
    public JoueurGoal(String nom, Ballon ballonDuJeu, Equipe monEquipe, Equipe equipeAdverse) {

        //appel du constructeur parent, notamment pour ajouter les équipes
        super(nom, ballonDuJeu, monEquipe, equipeAdverse);

        placerJoueur();

        //modification de la distance d'interception du gardien en fonction de la longueur des cages
        caracteristiques.setDistMinPrendreBalle(monEquipe.getCage().getLongueur()/5);
        caracteristiques.setDistMaxTir(Terrain.getDimTerrain().width/3);
        caracteristiques.setDistDep(5);

        //Délimitation de sa zone de déplacement
        yMin = (int) monEquipe.getCage().getCoordonnees().getY();
        yMax = yMin + monEquipe.getCage().getLongueur();


    }

/*******************************  CONSTRUCTEUR  *******************************/

    /**
     * Place le goal dans la cage de gauche ou de droite
     */
    public void placerJoueur(){

        //positionnement du goal au milieu de ses cages
        //on différencie les deux cas, si le goal est dans la cage de gauche ou de droite
        int xCage = (int) monEquipe.getCage().getCoordonnees().getX();

        //si l'angle supérieur gauche de la cage est à gauche
        if(xCage < Terrain.LARGEUR/2)
            x = xCage + monEquipe.getCage().getLargeur();
        else
            x = xCage; //abscisse de l'angle supérieur gauche de la cage

        y = (int) monEquipe.getCage().getCoordonnees().getY() + (monEquipe.getCage().getLongueur()/2);

        setAngleSelonBallon();
        
    }

/****************************** BOUCLE DE THREAD ******************************/

    /**
     * Boucle principal du goal
     */
    @Override
    public void demarrerJoueur() {

        
        while(!threadEstTermine){

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
            if(isValideDistCollision(new Point(x,tmpY)))
                y = tmpY;


            if(this == ballonDuJeu.getPossesseur()) ballonDuJeu.majXY();

            try {
                sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(this == ballonDuJeu.getPossesseur()){

                Joueur unJoueur  = passeAUnCoequipier();

                if(unJoueur != null){
                    ballonDuJeu.changerDePossesseur(unJoueur);
                    ballonDuJeu.majXY();
                }
            }

        }
        
        
    }



}