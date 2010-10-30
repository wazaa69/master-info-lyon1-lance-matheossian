package Model.Strategies;

import Model.ElementMobile.Joueur;
import Model.Equipe;
import Model.Terrain.Terrain;
import java.util.ArrayList;

/**
 * Chaque fils contiendra au moins la méthode uiliserStrat().
 */

public abstract class Strategie {

    ArrayList<Integer> formation; /** la formation adoptée par l'équipe */


    /**
     * Méthode qui sera écrite dans les classes enfants
     * @param unJoueur un joueur du match
     */
    public abstract void utiliserStrat(Joueur unJoueur);


     /**
     * positionnement des joueurs sur le terrain, selon la formation choisit
     */
    public void positionnerUneEquipe(Equipe equipeAplacer, Equipe equipeAdverse){

        //recupération de l'abscisse, des angles sup gauche de chaque cage
        int xCage = (int) equipeAplacer.getCage().getCoordonnees().getX();
        int xCageAdverse = (int) equipeAdverse.getCage().getCoordonnees().getX();

        //récupération de la liste de joueurs à placer
        ArrayList<Joueur> listeJoueurs = equipeAplacer.getListeJoueurs();

        /*
         * il faudrait vérifier que le nombre de joueurs par colonne est correcte,
         * sinon, il faut subdiviser ce nombre en deux, tant que le nombre max de
         * joueur n'est pas respectée. Dépend aussi de la distance de colision !
         */

        //création des coordonnées et de l'angle
        int y;
        int x;

        int tailleColonne = (int) Terrain.getDimTerrain().getWidth()/formation.size();
        int largeurTerrain = (int) Terrain.getDimTerrain().getHeight();

        //Création du point de départ pour positionner les premiers attaquants
        //int xDepart = xCageAdverse - Terrain.MARGESEINTE; //ajout d'une marge


        int i = 1; //on passe le goal

        //La cage de l'équipe adverse est à gauche
        if(xCageAdverse < xCage){

            //ajout des joueurs, selon le nombre définie dans la formation
            for(int j = 0; j < formation.size() && i < listeJoueurs.size(); j++){

                //i ème colonne de joueurs
                x = tailleColonne*j + Terrain.MARGESEINTE*3; //ajout de 3*la marge intérieur pour s'éloigner du goal

                for(int k = 0; k < formation.get(j) && i < listeJoueurs.size(); k++){

                    //j ème ligne
                    y = (largeurTerrain/(formation.get(j)+1)) * (k+1) + Terrain.MARGESEINTE;

                    //maj des coordonnées du joueur
                    listeJoueurs.get(i).setX(x);
                    listeJoueurs.get(i).setY(y);
                    listeJoueurs.get(i).setAngle(180);

                    i++; //passage au joueur suivant

                }
            }

        }
        else //sinon, la cages de l'équipe adverse est à droite
        {

            for(int j = 0; j < formation.size() && i < listeJoueurs.size(); j++){

                x = xCageAdverse - (tailleColonne*j + Terrain.MARGESEINTE*3);

                for(int k = 0; k < formation.get(j) && i < listeJoueurs.size(); k++){

                    y = (largeurTerrain/(formation.get(j)+1)) * (k+1) + Terrain.MARGESEINTE;

                    listeJoueurs.get(i).setX(x);
                    listeJoueurs.get(i).setY(y);

                    i++;

                }
            }
        }



    }

}
