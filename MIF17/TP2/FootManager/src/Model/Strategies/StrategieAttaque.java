package Model.Strategies;

import Model.ElementMobile.Caracteristiques;
import Model.ElementMobile.Ballon;
import Model.ElementMobile.Joueur;
import java.awt.Point;

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
public class StrategieAttaque implements Strategie {

    public void utiliserStrat(Joueur unJoueur){

        Ballon ballonDuJeu = unJoueur.getBallonDuJeu();
        Joueur possesseur = ballonDuJeu.getPossesseur();
        Joueur ancienPoss= ballonDuJeu.getAncienPoss();
        
        int distanceAuBallon = unJoueur.getDistance(new Point(unJoueur.getX(),unJoueur.getY()),ballonDuJeu);

        Caracteristiques caractUnJoueur = unJoueur.getCaracteristiques();

        if(possesseur != unJoueur){
            if(distanceAuBallon <= caractUnJoueur.getDistMinPrendreBalle() && unJoueur != ancienPoss){
                ballonDuJeu.setAncienPoss(possesseur);
                ballonDuJeu.passerLeBallonA(unJoueur);
            }
            
        }

        if(possesseur == unJoueur){
            unJoueur.deplacementAuHasard();
            unJoueur.getBallonDuJeu().majXY();
        }
        else if(distanceAuBallon > caractUnJoueur.getDistMinPrendreBalle()*2)
            unJoueur.deplacementAuHasard();

        else unJoueur.deplacementVersballon();

    }

}
