package Model;

/**
 * Le joueur représentant le goal, il pourra se déplacer uniquement sur un axe.
 */
public class JoueurGoal extends Joueur {

    int yMin; /** valeur minimal en ordonné pour rester devant les cages */
    int yMax;  /** valeur maximal en ordonné pour rester devant les cages */

    public JoueurGoal(String nom, Equipe monEquipe, Equipe equipeAdverse) {

        //appel du constructeur parent, notamment pour ajouter les équipes
        super(nom, monEquipe, equipeAdverse);

        //positionnement du goal au milieu de ses cages
        //on différencie les deux cas, si le goal est dans la cage de gauche ou de droite
        int xCage = (int) monEquipe.getCage().getCoordonnees().getX();

        if(xCage < Terrain.LARGEUR/2){
            x = xCage + monEquipe.getCage().getLargeur();
            angle = 0;
        }
        else{
            x = xCage - monEquipe.getCage().getLargeur();
            angle = 180;
        }

        y = (int) monEquipe.getCage().getCoordonnees().getY() + (monEquipe.getCage().getLongueur()/2);


        //délimitation de sa zone de déplacement
        yMin = (int) monEquipe.getCage().getCoordonnees().getY();
        yMax = yMin + monEquipe.getCage().getLongueur();

        //choix de l'angle
        angle = setAngleSelonBallon();
    }

    @Override
    public void demarrerJoueur() {
        
    }   

}
