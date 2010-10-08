package Model;

import java.awt.Color;
import java.util.ArrayList;

public class JeuDeFoot extends Thread {

    private Equipe equipeUne; /** première équipe */
    private Equipe equipeDeux; /** seconde équipe */

    private Terrain unTerrain; /** le terrain de jeu */

    private boolean partieTerminee = true; /** vrai si la partie est terminée, faux sinon */
    private boolean pause = true; /** vrai si le jeu de Foot est en pause, faux sinon */

    /**
     * Constructeur, initialise les deux équipes, les joueurs, le terrain et la balle
     */
    public JeuDeFoot() {

        this.equipeUne = new Equipe(Color.BLUE);
        this.equipeDeux = new Equipe(Color.RED);

        for(int i = 0; i < 11; i++){
            equipeUne.ajouterUnJoueur(new Joueur("Bleu" + (i + 1), equipeUne));
            equipeDeux.ajouterUnJoueur(new Joueur("Rouge" + (i + 1), equipeDeux));
        }

        this.unTerrain = new Terrain(500, 500, Color.WHITE);

        //ajouetr 1 stratégie/equipe

    }


    /**
     * Lance le jeu
     */
    public void lancerThreadJeuDeFoot() {

        System.out.println("Lancement du thread");

        //Lance le Thread de Jeu de Foot
        (new Thread(this)).start();
    }

    /**
     * Boucle de jeu
     */
    @Override
    public void run(){demarrerLaPartie();}


    /**
     * Lance les thread de chaque joueurs
     */
    private synchronized void demarrerLaPartie() {

        partieTerminee = false;
        pause = false;

        ArrayList<Joueur> listeJoueurEquUne = equipeUne.getListeJoueurs();
        ArrayList<Joueur> listeJoueurEquDeux = equipeDeux.getListeJoueurs();

        for(int i = 0; i < listeJoueurEquUne.size(); i++)
            listeJoueurEquUne.get(i).start();

        for(int i = 0; i < listeJoueurEquDeux.size(); i++)
            listeJoueurEquDeux.get(i).start();
        
    }

    /**
     * @return Retourne le terrain de foot
     */
    public Terrain getUnTerrain() {return unTerrain;}

    /**
     * Remet à zéro les variable du jeu
     */
    public void creer() {
        partieTerminee = false;
        pause = false;

    }

    /**
     * TODO
     * Active/Désactive la pause
     * @param pause vrai pour dire que le jeu de Foot est en pause, faux sinon
     */
    public synchronized void setPause(boolean pause) {
    
        this.pause = pause;

        //Tant que la reprise n'a pas été décidée par l'utilisateur, faire une pause pour chaque joueur

    }

    public void setPartieTerminee(boolean partieTerminee) {this.partieTerminee = partieTerminee;}

    public boolean isPartieTerminee() {return partieTerminee;}

    
}
