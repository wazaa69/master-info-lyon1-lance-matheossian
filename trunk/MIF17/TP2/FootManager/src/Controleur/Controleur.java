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

                if(str.equals("Démarrer/Relancer")){
                    if(isEnCoursDePartie())
                        unJeuDeFoot.setPause(false);
                    else
                        unJeuDeFoot.lancerThreadJeuDeFoot();
                }

                else if(str.equals("Pause") && unJeuDeFoot != null)
                    unJeuDeFoot.setPause(true);

                else if(str.equals("Mise à zéro"))
                    unJeuDeFoot.creer();

                else
                    System.exit(0);
                    
            }

        } );
    }


    /**
     * @return retourne vrai si le jeu de Foot est lancé
     */
    private boolean isEnCoursDePartie(){
        return (unJeuDeFoot != null && unJeuDeFoot.isPartieTerminee());
    }

}