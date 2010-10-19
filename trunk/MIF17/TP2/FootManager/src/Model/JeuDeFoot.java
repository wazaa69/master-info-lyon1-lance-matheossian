package Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe qui gère un jeu de foot
 */
public class JeuDeFoot extends Thread {


    private Date horloge; /**  horloge pour gérer le temps de jeu */

    private Equipe equipeUne; /**   première équipe */
    private Equipe equipeDeux; /**   seconde équipe */

    private static Ballon unBallon; /**  le ballon de foot disponible */

    private Terrain unTerrain; /**  le terrain de jeu */

    private boolean partieEnCours; /**   vrai si la partie est en cours, faux sinon */
    private boolean pauseRepartir; /**   vrai si le jeu de Foot est en pause, faux sinon */


    /**
     * Constructeur, initialise les deux équipes, les joueurs, le terrain et la balle
     * @param longueurTerrain
     * @param largeurTerrain
     */
    public JeuDeFoot(int longueurTerrain, int largeurTerrain) {

        unTerrain = new Terrain(longueurTerrain, largeurTerrain, Color.WHITE);

        unBallon = new Ballon(Math.round(longueurTerrain/2), Math.round(largeurTerrain/2), Color.MAGENTA);

        initEquipes();

        partieEnCours = false;

        pauseRepartir = false;
    }


/***********************   Initialisation des Equipes  ************************/


    private void initEquipes(){

        int nbJoueurs = 11;
        
        equipeUne = new Equipe(Color.BLUE);
        equipeDeux = new Equipe(Color.RED);

        initJoueursEquipe(equipeUne,equipeDeux, nbJoueurs);
        initJoueursEquipe(equipeDeux, equipeUne, nbJoueurs);

    }

    /**
     * Créé un certain nombre de Joueurs et les ajoutes dans une équipe.
     * Chaque joueur est placé aléatoirement sur le terrain.
     * @param courante l'équipe qui doit acqueuillir de nouveaux joueurs
     * @param adverse l'équipe adverse
     * @param nbJoueurs le nombre de joueur dans l'équipe
     */
    private void initJoueursEquipe(Equipe courante, Equipe adverse, int nbJoueurs){
        
        int angle = 0;
        Point unPoint = null;
        Joueur unJoueur = null;

        for(int i = 0; i < nbJoueurs; i++){
            while(unPoint == null) {unPoint = pointHasardDsTerrain();}
            angle = (int) (Math.random() * 360);
            unJoueur = new Joueur((int) unPoint.getX(), (int) unPoint.getY(), angle, "Rouge" + (i + 1), courante, adverse);
            courante.ajouterUnJoueur(unJoueur);
            unPoint = null;
        }

    }

    /**
     * Recherche un point et une zone au hasard sur le terrain,
     * qui n'est pas déjà occupé par un autre joueur.
     * @return retourne le point libre si il est dispponible, null sinon
     */
    private Point pointHasardDsTerrain(){


        //on récupère chaque équipe
        ArrayList<Joueur> listeJoueurEquUne = equipeUne.getListeJoueurs();
        ArrayList<Joueur> listeJoueurEquDeux = equipeDeux.getListeJoueurs();

        //on concatène les deux listes
        ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>(listeJoueurEquUne);
        listeJoueurs.addAll(listeJoueurEquDeux);


        Dimension dim = Terrain.getDimTerrain();
        Point unPoint = new Point(
                    (int) Math.round(Math.random() * dim.getWidth()) + Terrain.MARGESEINTE,
                    (int) Math.round(Math.random() * dim.getHeight()) + Terrain.MARGESEINTE
                    );


        boolean pasDeContact = true;

        for(int i = 0; i < listeJoueurs.size() && pasDeContact; i++){
            if(!listeJoueurs.get(i).isValideDistContact(unPoint))
                pasDeContact = !pasDeContact;
        }

        if(pasDeContact)return unPoint;
        else return null;
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
     * Active/Désactive la pause
     */
    public synchronized void setPauseRepartir() {

        //faux de base
        pauseRepartir = !pauseRepartir;

        ArrayList<Joueur> listeJoueurEquUne = equipeUne.getListeJoueurs();
        ArrayList<Joueur> listeJoueurEquDeux = equipeDeux.getListeJoueurs();



        if(pauseRepartir){
            for(int i = 0; i < listeJoueurEquUne.size(); i++)
                listeJoueurEquUne.get(i).setEstEnpause();

            for(int i = 0; i < listeJoueurEquDeux.size(); i++)
                listeJoueurEquDeux.get(i).setEstEnpause();
        }
        else
        {
            
            for(int i = 0; i < listeJoueurEquUne.size(); i++){
                listeJoueurEquUne.get(i).setEstEnpause();     
            }

            for(int i = 0; i < listeJoueurEquDeux.size(); i++){
                listeJoueurEquDeux.get(i).setEstEnpause();
            }
             
        }



    }


/*********************************   METHODES  *********************************/


    /**
     * Remet à zéro les variable du jeu
     */
    public void creer() {
        partieEnCours = false;
        pauseRepartir = false;
    }


/******************************  GETTER/SETTERS  ******************************/


    public Equipe getEquipeUne() {return equipeUne;}
    public Equipe getEquipeDeux() {return equipeDeux;}

    public Terrain getUnTerrain() {return unTerrain;}
    public Ballon getUnBallon() {return unBallon;}

    public void setPartieTerminee(boolean partieEncours) {this.partieEnCours = partieEncours;}
    public boolean isPartieEnCours() {return partieEnCours;}


    
}
