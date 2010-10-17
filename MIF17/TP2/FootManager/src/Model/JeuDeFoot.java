package Model;

import java.awt.Color;
import java.util.ArrayList;

/**
 * La classe qui gère un jeu de foot
 */
public class JeuDeFoot extends Thread {

    private Equipe equipeUne; /** @param equipeUne première équipe */
    private Equipe equipeDeux; /** @param equipeDeux seconde équipe */

    private Terrain unTerrain; /** @param unTerrain le terrain de jeu */

    private boolean partieEnCours; /** @param partieEnCours vrai si la partie est en cours, faux sinon */
    private boolean pause; /** @param pause vrai si le jeu de Foot est en pause, faux sinon */


    /**
     * Constructeur, initialise les deux équipes, les joueurs, le terrain et la balle
     * @param longueurTerrain
     * @param largeurTerrain
     */
    public JeuDeFoot(int longueurTerrain, int largeurTerrain) {

        int x = Math.round(longueurTerrain/2);
        int yCentre = Math.round(largeurTerrain/2);

        this.equipeUne = new Equipe(Color.BLUE);
        this.equipeDeux = new Equipe(Color.RED);

        this.unTerrain = new Terrain(longueurTerrain, largeurTerrain, Color.WHITE);

        for(int i = 0; i < 11; i++){
            equipeUne.ajouterUnJoueur(new Joueur(x, yCentre, "Bleu" + (i + 1), equipeUne));
            equipeDeux.ajouterUnJoueur(new Joueur(x, yCentre, "Rouge" + (i + 1), equipeDeux));
        }

        partieEnCours = false;
        pause = false;

        //ajouter un comportement par tortue
        //ajouetr une stratégie par equipe

    }


/**********************************  THREAD  **********************************/

    /**
     * Lance le jeu
     */
    public void lancerThreadJeuDeFoot() {

        System.out.println("Lancement du thread de Jeu");

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
    private void demarrerLaPartie() {

        partieEnCours = true;

        ArrayList<Joueur> listeJoueurEquUne = equipeUne.getListeJoueurs();
        ArrayList<Joueur> listeJoueurEquDeux = equipeDeux.getListeJoueurs();

        for(int i = 0; i < listeJoueurEquUne.size(); i++)
            listeJoueurEquUne.get(i).start();

        for(int i = 0; i < listeJoueurEquDeux.size(); i++)
            listeJoueurEquDeux.get(i).start();

    }

    /**
     * TODO
     * Active/Désactive la pause
     * @param pause vrai pour dire que le jeu de Foot est en pause, faux sinon
     */
    public synchronized void setPause(boolean pause) {

        System.out.println("Pause : " + pause);
        this.pause = pause;

        //Tant que la reprise n'a pas été décidée par l'utilisateur, faire une pause pour chaque joueur

    }


/*********************************   METHODES  *********************************/

    /**
     * @return Retourne le terrain de foot
     */
    public Terrain getUnTerrain() {return unTerrain;}



    /**
     * Remet à zéro les variable du jeu
     */
    public void creer() {
        partieEnCours = false;
        pause = false;
    }


/******************************  GETTER/SETTERS  ******************************/


    public boolean isPause() {return pause;}


    public void setPartieTerminee(boolean partieEncours) {this.partieEnCours = partieEncours;}
    public boolean isPartieEnCours() {return partieEnCours;}


    public Equipe getEquipeUne() {return equipeUne;}
    public Equipe getEquipeDeux() {return equipeDeux;}
    
}
