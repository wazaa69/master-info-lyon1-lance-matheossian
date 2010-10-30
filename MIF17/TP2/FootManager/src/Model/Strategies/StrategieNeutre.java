package Model.Strategies;

import Model.ElementMobile.Joueur;
import Model.Equipe;

/**
 *
 */
public class StrategieNeutre extends Strategie {

    public void utiliserStrat(Joueur unJoueur){}

    @Override
    public void positionnerUneEquipe(Equipe equipeAplacer, Equipe equipeAdverse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
