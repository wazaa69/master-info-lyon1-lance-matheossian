package Model;

import java.awt.Color;

public class JeuDeFoot {

    private Equipe equipeUne;
    private Equipe equipeDeux;

    private Terrain unTerrain;

    public JeuDeFoot() {

        this.equipeUne = new Equipe(Color.BLUE);
        this.equipeDeux = new Equipe(Color.RED);

        for(int i = 0; i < 11; i++){
            equipeUne.ajouterUnJoueur(new Joueur("Bleu" + (i + 1), equipeUne));
            equipeDeux.ajouterUnJoueur(new Joueur("Rouge" + (i + 1), equipeDeux));
        }

        this.unTerrain = new Terrain(500, 500, Color.WHITE);

        //1 stratégie/equipe
 
    }

    public void lancerPartie() {
        System.out.println("Boucle de jeu !! Youpi");
    }

    public Terrain getUnTerrain() {
        return unTerrain;
    }
}
