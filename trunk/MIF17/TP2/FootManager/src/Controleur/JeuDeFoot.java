package Controleur;

import Model.Equipe;
import Model.Joueur;
import Model.Terrain;
import Vue.Vue;
import java.awt.Color;

public class JeuDeFoot {

    private Vue vue;

    private Equipe equipeUne;
    private Equipe equipeDeux;

    private Terrain unTerrain;

    public JeuDeFoot(Vue vue, int nbrJoueurs) {
        this.vue = vue;
        initJeuDeFoot(nbrJoueurs);

    }

    private void initJeuDeFoot(int nbrJoueurs) {
        
        equipeUne = new Equipe(Color.BLUE);
        equipeDeux = new Equipe(Color.RED);

        for(int i = 0; i < 22; i++){
            equipeUne.ajouterUnJoueur(new Joueur("Bleu" + i, equipeUne));
            equipeDeux.ajouterUnJoueur(new Joueur("Rouge" + i, equipeDeux));
        }

        this.unTerrain = new Terrain(500, 500, Color.WHITE);


        //1 stratégie/equipe

        vue.initVue();
    }

}
