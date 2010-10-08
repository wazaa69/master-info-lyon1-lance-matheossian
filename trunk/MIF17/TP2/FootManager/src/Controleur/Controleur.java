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
        
        //Ajout d'un observateur sur la vue. Le controleur Observe la vue.
        this.uneVue.ajouterObserveur(new ObservateurBouton() {

            //L'observateur va réaliser différentes actions
            public void miseAJour(String str){

                if(str.equals("Démarrer")){
                    if(isPartieEnCours())
                        unJeuDeFoot.setPause(false);
                    else
                        unJeuDeFoot.lancerThreadJeuDeFoot();
                }

                else if(str.equals("Pause/Repartir")&& isPartieEnCours())
                    unJeuDeFoot.setPause(!unJeuDeFoot.isPause());

                else if(str.equals("Mise à zéro"))
                    unJeuDeFoot.creer();

                else if(str.equals("Quitter"))
                    System.exit(0);
                    
            }

        } );
    }


    /**
     * @return retourne vrai si le jeu de Foot est lancé (donc qu'il existe aussi)
     */
    private boolean isPartieEnCours(){
        return (unJeuDeFoot != null && unJeuDeFoot.isPartieEnCours());
    }

}