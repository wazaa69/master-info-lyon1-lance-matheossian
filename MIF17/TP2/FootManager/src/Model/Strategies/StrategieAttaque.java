package Model.Strategies;

import Model.Joueur;

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

    public static void utiliserStrat(Joueur unJoueur){
        
    }


}
