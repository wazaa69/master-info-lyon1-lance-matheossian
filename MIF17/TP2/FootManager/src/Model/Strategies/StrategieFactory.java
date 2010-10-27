package Model.Strategies;

/**
 *  Cette classe va créer la bonne instance de stratégie
 */
public class StrategieFactory {

    /**
     * Crée une stratégie en fonction du choix de stratégie passé en paramètre
     * @param choixStrat le numero de la stratégie à créer = le discriminateur
     * @return retourne une stratégie
     */
    public Strategie creerStrategie(int choixStrat){

        switch(choixStrat){
            case 0: //attaque
                return new StrategieAttaque();
            case 1: //neutre
                return new StrategieNeutre();
            case 2: //defense
                return new StrategieDefense();

            default:
                return new StrategieNeutre();
        }

    }

}
