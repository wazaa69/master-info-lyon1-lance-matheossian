package Controleur;

import Model.JeuDeFoot;
import ObservListe.ObservateurBouton;

import Vue.Vue;


public class Controleur {

    private Vue uneVue;
    private JeuDeFoot unJeuDeFoot;

    Controleur(JeuDeFoot _unJeuDeFoot, Vue uneVue) {
        this.uneVue = uneVue;

        this.unJeuDeFoot = _unJeuDeFoot;
        
        //Ajout d'un observateur sur la vue. Le controleur Observe la vue
        this.uneVue.ajouterObserveur(new ObservateurBouton() {

            //Ce qui va réaliser l'action
            public void miseAJour(String str){
                //différencier les cas(on pourra appeler une méthode
                unJeuDeFoot.lancerPartie();
            }

            //ne pas implémanter
            public void miseAJour() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        } );


    }

    

}
