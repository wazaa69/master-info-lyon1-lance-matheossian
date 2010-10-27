package Controleur;

import Model.Equipe;
import Model.JeuDeFoot;
import Model.Strategies.Strategie;
import ObservListe.ObserveurComboBox;
import Vue.Controls.FenetreControls;

/**
 * Le controleur qui gère le changement de stratégie
 */
public class ControleurStrategie{

    JeuDeFoot unJeuDeFoot;

    public ControleurStrategie(JeuDeFoot unJeuDeFoot, FenetreControls uneFenetreControl) {

        this.unJeuDeFoot = unJeuDeFoot;


        uneFenetreControl.ajouterObserveur(new ObserveurComboBox() {

            public void miseAJour(Equipe uneEquipe, Strategie strategie) {
                uneEquipe.setStartegie(strategie);
            }
        });


    }


}
