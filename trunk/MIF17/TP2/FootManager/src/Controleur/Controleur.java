package Controleur;

import Model.JeuDeFoot;
import Vue.Vue;

/**
 * Le controleur qui va créer tous les autres controleurs
 */
public class Controleur {

    public Controleur(JeuDeFoot unJeuDeFoot, Vue uneVue) {
        ControleurControles controleurDesControls = new ControleurControles(unJeuDeFoot, uneVue);
        ControleurStrategie controleurDesStrategies = new ControleurStrategie(unJeuDeFoot, uneVue.getFenetreControls());
    }

}
