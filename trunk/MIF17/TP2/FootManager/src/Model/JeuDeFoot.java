package Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 * La classe qui gère un jeu de foot
 */
public class JeuDeFoot extends Thread {


    private long horlogeDebutMatch; /**  horloge pour gérer le temps de jeu */

    private Equipe equipeUne; /**   première équipe */
    private Equipe equipeDeux; /**   seconde équipe */

    private Terrain unTerrain; /**  le terrain de jeu */

    private boolean partieEnCours; /**   vrai si la partie est en cours, faux sinon */
    private boolean pauseRepartir; /**   vrai si le jeu de Foot est en pause, faux sinon */


    // -> voir si on met une ref dans Joueur au lieu du ballon static ! */
    public static Ballon UNBALLON; /**  le ballon de foot disponible */


/*******************************  CONSTRUCTEUR  *******************************/


    /**
     * Constructeur, initialise les deux équipes, les joueurs, le terrain et la balle
     * @param longueurTerrain
     * @param largeurTerrain
     */
    public JeuDeFoot(int longueurTerrain, int largeurTerrain) {

        partieEnCours = false;
        pauseRepartir = false;

        unTerrain = new Terrain(longueurTerrain, largeurTerrain, Color.WHITE);

        UNBALLON = new Ballon(Math.round(longueurTerrain/2), Math.round(largeurTerrain/2), Color.MAGENTA);

        initEquipes();

    }


/***********************   Initialisation des Equipes  ************************/


    private void initEquipes(){

        int nbJoueurs = 11;
        
        equipeUne = new Equipe("RedTeam",Color.BLUE, unTerrain.getCageGauche());
        equipeDeux = new Equipe("BlueTeam",Color.RED, unTerrain.getCageDroite());

        initJoueursEquipe(equipeUne, equipeDeux, nbJoueurs);
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
        
        Point unPoint = null;
        String nom = "";
        Joueur unJoueur = null;

        //Création du goal
        nom = courante.getNomEquipe() + " - Goal";
        unJoueur = new JoueurGoal(nom, courante, adverse);
        courante.ajouterUnJoueur(unJoueur);

        //création du reste de l'équipe, on commence à 1
        for(int i = 1; i < nbJoueurs; i++){

            while(unPoint == null) {unPoint = pointHasardDsTerrain();}

            nom = courante.getNomEquipe() + " - " + (i + 1);

            //création du joueur
            unJoueur = new Joueur((int) unPoint.getX(), (int) unPoint.getY(), nom, courante, adverse);

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

        //initialisation de l'horloge, à partir de l'heure actuelle (en millisecondes)
        horlogeDebutMatch = System.currentTimeMillis();

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
    public Ballon getUnBallon() {return UNBALLON;}

    public void setPartieTerminee(boolean partieEncours) {this.partieEnCours = partieEncours;}
    public boolean isPartieEnCours() {return partieEnCours;}

    /**
     * Retourne le temps écoulé depuis le début du match
     * @return retourne un long qui correspond au temps de match
     */
    public long tempsEcoule(){
        return horlogeDebutMatch - System.currentTimeMillis();
    }
  
}
