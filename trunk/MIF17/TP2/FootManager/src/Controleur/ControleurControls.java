package Controleur;

import Model.JeuDeFoot;
import ObservListe.ObserveurBouton;
import Vue.Vue;


public class ControleurControls {

    private Vue uneVue;
    private JeuDeFoot unJeuDeFoot;

    /**
     * Ce controleur gère les évènements reçu du GUI
     * @param jeuDeFoot le jeu de foot principal
     * @param uneVue la vue qui englobe toutes les autres vues
     */
    public ControleurControls(JeuDeFoot jeuDeFoot, Vue uneVue) {

        this.uneVue = uneVue;

        this.unJeuDeFoot = jeuDeFoot;


        //Ajout d'un observateur sur la vue. Le controleur Observe la vue.
        this.uneVue.getFenetreControls().ajouterObserveur(new ObserveurBouton() {

            //L'observateur va réaliser différentes actions
            public void miseAJour(String str){

                if(str.equals("Démarrer") && !isPartieEnCours())
                    unJeuDeFoot.lancerThreadJeuDeFoot();

                else if(str.equals("Pause/Repartir")&& isPartieEnCours())
                    unJeuDeFoot.setPauseRepartir();

                
                else if(str.equals("Quitter"))
                    System.exit(0);
                    
            }

        } );
    }


    /**
     * @return retourne vrai si le jeu de Foot est lancé (donc qu'il existe)
     */
    private boolean isPartieEnCours(){
        return (unJeuDeFoot != null && unJeuDeFoot.isPartieEnCours());
    }

}