package Controleur;

import Model.JeuDeFoot;
import ObservListe.ObservateurComboBox;
import Vue.FenetreControls;

/**
 * Le controleur qui gère le changement de stratégie
 */
public class ControleurStrategie{

    JeuDeFoot unJeuDeFoot;

    public ControleurStrategie(JeuDeFoot unJeuDeFoot, FenetreControls uneFenetreControl) {

        this.unJeuDeFoot = unJeuDeFoot;

        /*
        uneFenetreControl.ajouterObserveur(new ObservateurComboBox() {

            public void miseAJour(int element, String action) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
         */

    }


}
