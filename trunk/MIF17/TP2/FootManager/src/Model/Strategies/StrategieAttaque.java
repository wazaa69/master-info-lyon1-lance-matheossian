package Model.Strategies;

import Model.ElementMobile.Caracteristiques;
import Model.ElementMobile.Ballon;
import Model.ElementMobile.Joueur;
import Model.Equipe;
import Model.Terrain.Terrain;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Stratégie d'attaque :
 *
 *      Si le ballon arrive du côté adverse et que le possesseur est un joueur de l'équipe :
 *          - Les joueurs en défense iront en millieu de terrain
 *          - Les millieux de terrains iront en attaque avec les attaquants de base
 *
 *      Si le ballon est perdu, on repasse les joueurs en mode defense, tout en essayant
 *      de les faire prendre la balle.
 *
 *      Si l'utilisateur n'a pas changé de stratégie :
 *           - tant que les joueurs n'ont pas récupérés la balle, on ne repasse pas en attaque
 *
 *      Les joueurs proches du pocesseur tenent d'intercepter la balles.
 */
public class StrategieAttaque extends Strategie {


    public StrategieAttaque() {
        formation = new ArrayList<Integer>(); //4-5-1
        formation.add(new Integer(1)); //1 attaquant
        formation.add(new Integer(5)); //5 milieux
        formation.add(new Integer(4)); //4 défenseurs

    }

    public void utiliserStrat(Joueur unJoueur){

        
        Ballon ballonDuJeu = unJoueur.getBallonDuJeu();
        Joueur possesseur = ballonDuJeu.getPossesseur();
        Joueur ancienPoss= ballonDuJeu.getAncienPoss();
        int distanceAuBallon = unJoueur.getDistance(new Point(unJoueur.getX(),unJoueur.getY()),ballonDuJeu);
        boolean peuPossederBallon = unJoueur != ancienPoss && unJoueur != possesseur;

        Caracteristiques caractUnJoueur = unJoueur.getCaracteristiques();

        //le joueur essaye d'intercepter le ballon
        if (distanceAuBallon <= caractUnJoueur.getDistMinPrendreBalle()
                && peuPossederBallon){

            //interception ?
            if(Math.random() > unJoueur.getCaracteristiques().getProbaRecupBallon()){
                ballonDuJeu.passerLeBallonA(unJoueur);
            }
        }

        else if(distanceAuBallon <= caractUnJoueur.getDistMinPrendreBalle()*3
                && peuPossederBallon){
            unJoueur.setAngleSelonBallon();
            unJoueur.avancer();
        }

        //le joueur possede le ballon
        else if(possesseur == unJoueur)
        {
            unJoueur.setAngleSelon(unJoueur.getEquipeAdverse().getGoal().getXY());
            unJoueur.avancer();
            unJoueur.getBallonDuJeu().majXY();
        }

        //le joueur est trop loin du ballon
        else unJoueur.deplacementAuHasard();

         

        
    }


}
