package Controleur;

import Model.JeuDeFoot;
import Vue.Vue;

/**
 * 
 * Jeu de Foot basique avec quelques stratégies.
 * 
 * @version 2.0
 * @author LANCE Florian  n°Etu : 10604720
 * @author MATHEOSSIAN Dimitri n°Etu : 10604950
 */
public class Main {

    public static void main(String[] args) {

        //Initialisation du Modèles. Le modèle ne connait personne.
        JeuDeFoot unJeuDeFoot = new JeuDeFoot(500,375,11);

        //Initialise la fenêtre et boutons. La vue connait le modèle.
        Vue uneVue = new Vue(unJeuDeFoot);

        //Initialise les controleurs. Le controleur connait tout le monde.
        Controleur controleur = new Controleur(unJeuDeFoot, uneVue);
        ControleurStrategie controleurStrategie = new ControleurStrategie(unJeuDeFoot, uneVue.getFenetreControls());

    }

}
