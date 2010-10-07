package Controleur;

import Model.JeuDeFoot;
import Vue.Vue;


public class Main {

    public static void main(String[] args) {

        //Initialisation du Modèles. Le modèle ne connait personne.
        JeuDeFoot unJeuDeFoot = new JeuDeFoot();

        //Initialise la fenêtre et boutons. La vue connait le modèle.
        Vue uneVue = new Vue(unJeuDeFoot);

        //Initialise le controleur, Le controleur connait tout le monde.
        Controleur controleur = new Controleur(unJeuDeFoot, uneVue);

    }


}
