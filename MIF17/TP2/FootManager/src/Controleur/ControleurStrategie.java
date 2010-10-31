package Controleur;

import Model.Equipe;
import Model.JeuDeFoot;
import Model.Strategies.Strategie;
import ObservListe.ObserveurComboBox;
import Vue.Controles.FenetreControles;

/**
 * Le controleur qui gère le changement de stratégie
 */
public class ControleurStrategie{

    JeuDeFoot unJeuDeFoot;

    /**
     * Ce controleur gère les changements de stratégies
     * @param jeuDeFoot le jeu de foot principal
     * @param uneFenetreControl la vue qui pemet de gérer les intercation avec l'utilisateur
     */
    public ControleurStrategie(JeuDeFoot jeuDeFoot, FenetreControles uneFenetreControl) {

        this.unJeuDeFoot = jeuDeFoot;

        uneFenetreControl.ajouterObserveur(new ObserveurComboBox() {

            public void miseAJour(Equipe uneEquipe, Strategie strategie) {

                uneEquipe.setStartegie(strategie);

                //Modification des positions de formation, de chaque joueur
                if(unJeuDeFoot.getEquipeGauche() == uneEquipe)
                    strategie.placerOuChangFormation(uneEquipe, unJeuDeFoot.getEquipeDroite(), true);
                else
                    strategie.placerOuChangFormation(uneEquipe, unJeuDeFoot.getEquipeGauche(), true);
            }
        });


    }


}
