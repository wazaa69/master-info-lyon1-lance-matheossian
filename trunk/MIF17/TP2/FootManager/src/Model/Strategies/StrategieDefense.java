package Model.Strategies;

import Model.ElementMobile.Joueur;
import java.util.ArrayList;

/**
 *
 */
public class StrategieDefense extends Strategie{

    public StrategieDefense() {
        formation = new ArrayList<Integer>(); //4-5-1
        formation.add(new Integer(1)); //1 attaquant
        formation.add(new Integer(4)); //5 milieux
        formation.add(new Integer(5)); //4 défenseurs
    }



    public void utiliserStrat(Joueur unJoueur){

    }

}
