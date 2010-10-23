package Model.Strategies;

import Model.Caracteristiques;
import Model.JeuDeFoot;
import Model.Joueur;
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
public class StrategieAttaque extends Strategie {

    public static synchronized void utiliserStrat(Joueur unJoueur){

        Joueur possesseur = JeuDeFoot.UNBALLON.getPossesseur();
        Joueur ancienPoss= JeuDeFoot.UNBALLON.getAncienPoss();
        
        int distanceAuBallon = unJoueur.getDistance(new Point(unJoueur.getX(),unJoueur.getY()),JeuDeFoot.UNBALLON);

        Caracteristiques caractUnJoueur = unJoueur.getCaracteristiques();

        if(possesseur != unJoueur){
            if(distanceAuBallon <= caractUnJoueur.getDistMinPrendreBalle() && unJoueur != ancienPoss){
                JeuDeFoot.UNBALLON.setAncienPoss(possesseur);
                JeuDeFoot.UNBALLON.setPossesseur(unJoueur);
            }
            
        }

        if(possesseur == unJoueur){
            unJoueur.deplacementAuHasard();
            JeuDeFoot.UNBALLON.majXY();
        }
        else if(distanceAuBallon > caractUnJoueur.getDistMinPrendreBalle()*2)
            unJoueur.deplacementAuHasard();

        else unJoueur.deplacementVersballon();
    }

}
