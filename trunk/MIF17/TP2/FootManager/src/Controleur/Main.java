package Controleur;

import Model.Equipe;
import Model.Joueur;
import Model.Terrain;
import Vue.Vue;
import java.awt.Color;

public class Main {

    public static void main(String[] args) {
        
        Equipe equipeUne = new Equipe(Color.BLUE);
        Equipe equipeDeux = new Equipe(Color.RED);

        for(int i = 0; i < 11; i++){
            equipeUne.ajouterUnJoueur(new Joueur("Bleu" + (i + 1), equipeUne));
            equipeDeux.ajouterUnJoueur(new Joueur("Rouge" + (i + 1), equipeDeux));
        }

        Terrain unTerrain = new Terrain(500, 500, Color.WHITE);

        //1 stratégie/equipe

        Vue vue = new Vue(unTerrain);
    }


}
