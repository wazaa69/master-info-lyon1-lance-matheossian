package Model;

import Model.ElementMobile.Ballon;
import Model.Terrain.Terrain;
import Model.ElementMobile.Joueur;
import Model.ElementMobile.JoueurGoal;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe qui gère un jeu de foot
 */
public class JeuDeFoot extends Thread {


    private long chronometre; /**  horloge pour gérer le temps de jeu */
    private int dureeDuMatch = 60; /** le temps que dure un match */

    private Equipe equipeGauche; /**   équipe à gauche sur le terrain */
    private Equipe equipeDroite; /**   équipe à droite sur le terrain */

    private Terrain unTerrain; /**  le terrain de jeu */

    private Ballon ballonDuJeu; /**  le ballon de foot */
    private int limiteDeButs = 3; /** nombre de but(s) maximal que peut marquer une équipe, le jeu s'arrête */

    private static boolean PARTIEENCOURS; /**   vrai si la partie est en cours, faux sinon */
    private boolean pauseRepartir; /**   vrai si le jeu de Foot est en pause, faux sinon */


/*******************************  CONSTRUCTEUR  *******************************/


    /**
     * Constructeur, initialise les deux équipes, les joueurs, le terrain et la balle
     * @param longueurTerrain
     * @param largeurTerrain
     * @param nbJoueursParEq le nombre de joueur par équipe
     */
    public JeuDeFoot(int longueurTerrain, int largeurTerrain, int nbJoueursParEq) {

        unTerrain = new Terrain(longueurTerrain, largeurTerrain, Color.WHITE);
        
        ballonDuJeu = new Ballon(0, 0, 5, Color.MAGENTA);
        
        ballonDuJeu.initBallon();

        initEquipes(nbJoueursParEq);

        //Important : à faire après avoir initialisé les équipes
        positionnerEquipes();

        initBoolsJeuDeFoot();

    }

    /**
     * Initialise les booléen : "partie en cours ?" et "pause ?"
     */
    private void initBoolsJeuDeFoot() {
        PARTIEENCOURS = false;
        pauseRepartir = false;
    }


/***********************   Initialisation des Equipes  ************************/


    private void initEquipes(int nbJoueurs){
        
        equipeGauche = new Equipe("RedTeam",Color.RED, unTerrain.getCageGauche());
        equipeDroite = new Equipe("BlueTeam",Color.BLUE, unTerrain.getCageDroite());

        initJoueursEquipe(equipeGauche, equipeDroite, nbJoueurs);
        initJoueursEquipe(equipeDroite, equipeGauche, nbJoueurs);

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
        int tmpNbJoueurs = nbJoueurs;

        if(nbJoueurs > 0){
            //Création du goal
            nom = courante.getNomEquipe() + " - Goal";
            unJoueur = new JoueurGoal(nom, ballonDuJeu, courante, adverse);
            courante.ajouterUnJoueur(unJoueur);
        }
        else if(nbJoueurs > 50) tmpNbJoueurs = 50;


        //création du reste de l'équipe, on commence à 1
        for(int i = 1; i < tmpNbJoueurs; i++){

            nom = courante.getNomEquipe() + " - " + (i + 1);

            //création du joueur
            unJoueur = new Joueur(0, 0, nom, ballonDuJeu, courante, adverse);

            courante.ajouterUnJoueur(unJoueur);
            
            unPoint = null;
            
        }

    }

/**********************************   Placement  ******************************/

    /**
     * Recherche un point et une zone au hasard sur le terrain,
     * qui n'est pas déjà occupé par un autre joueur.
     * @return retourne le point libre si il est dispponible, null sinon
     */
//    private Point pointHasardDsTerrain(){
//
//
//        //on concatène les deux listes
//        ArrayList<Joueur> listeJoueurs = getJoueurs();
//
//        Dimension dim = Terrain.getDimTerrain();
//        Point unPoint = new Point(
//                    (int) Math.round(Math.random() * dim.getWidth()) + Terrain.MARGESEINTE,
//                    (int) Math.round(Math.random() * dim.getHeight()) + Terrain.MARGESEINTE
//                    );
//
//
//        boolean pasDeContact = true;
//
//        for(int i = 0; i < listeJoueurs.size() && pasDeContact; i++){
//            if(!listeJoueurs.get(i).isValideDistContact(unPoint))
//                pasDeContact = !pasDeContact;
//        }
//
//        if(pasDeContact)return unPoint;
//        else return null;
//    }


    private void positionnerEquipes(){
        equipeGauche.getStartegie().placerOuChangFormation(equipeGauche, equipeDroite, false);
        equipeDroite.getStartegie().placerOuChangFormation(equipeDroite, equipeGauche, false);
    }

/**********************************  THREAD  **********************************/

    /**
     * Lance le jeu
     */
    public void lancerThreadJeuDeFoot() {

        System.out.println("Nouveau match");
        (new Thread(this)).start();

    }

    /**
     * Boucle de jeu, si le temps de match est écoulé, ou si le nombre de but(s)
     * maximum est atteint, on affiche le gagnant et on réinitialise les valeurs
     * du jeuDeFoot.
     */
    @Override
    public void run(){

        demarrerLaPartie();

        while(getTempsEcoule() <= dureeDuMatch && !isNbButMaxAtteint());

        afficherGagnantMatch();

        terminerThreadsJoueurs();

        try {
            sleep(2000); //le temps de voir les scores
        } catch (InterruptedException ex) {
            Logger.getLogger(JeuDeFoot.class.getName()).log(Level.SEVERE, null, ex);
        }

        resetJeuDeFoot();

    }

    

    /**
     * Lance les thread de chaque joueurs
     */
    private void demarrerLaPartie() {

        PARTIEENCOURS = true;

        demarrerChronometre(); //pour controler le temps de jeu

        //On récupère la liste des joueurs
        ArrayList<Joueur> listeJoueurs = getJoueurs();

        boolean etaitTermine = false;

        //Si le premier joueur est stopé, c'est que tous les autres le sont
        if (!listeJoueurs.isEmpty())
            etaitTermine = listeJoueurs.get(listeJoueurs.size()-1).getThreadEstTermine();

        //On positionne de nouveau les joueurs
        if(etaitTermine) positionnerEquipes();

        for(int i = 0; i < listeJoueurs.size(); i++){
            if(etaitTermine) listeJoueurs.get(i).setThreadEstTermine(false);
            listeJoueurs.get(i).lancerThread();
        }

    }

    /**
     * Active/Désactive la pause
     */
    public synchronized void setPauseRepartir() {

        //faux de base
        pauseRepartir = !pauseRepartir;

        ArrayList<Joueur> listeJoueurs = getJoueurs();

        for(int i = 0; i < listeJoueurs.size(); i++)
            listeJoueurs.get(i).setEstEnpause();

    }



/*********************************   METHODES  *********************************/


    /**
     * Initialisation du chronomètre, à partir de l'heure actuelle (en millisecondes)
     */
    private void demarrerChronometre(){
        chronometre = System.currentTimeMillis();
    }


/**************************   TESTE ET FIN DE MATCH  **************************/


    private boolean isNbButMaxAtteint(){
        return equipeGauche.getScore() >= limiteDeButs || equipeDroite.getScore() >= limiteDeButs;
    }


    /**
     * Affiche le gagnant du match
     */
    private void afficherGagnantMatch(){

        if(equipeGauche.getScore() > equipeDroite.getScore())
            System.out.println("L'équipe " + equipeGauche.getNomEquipe()
                    + " remporte le match !");

        else if (equipeGauche.getScore() < equipeDroite.getScore())
            System.out.println("L'équipe " + equipeDroite.getNomEquipe()
                    + " remporte le match !");
        
        else
            System.out.println("Le match se termine en nul !!");
        
    }


    private void terminerThreadsJoueurs(){
        //On termine les threads
        ArrayList<Joueur> listeJoueurs = getJoueurs();
        for(int i = 0; i < listeJoueurs.size(); i++)
            listeJoueurs.get(i).setThreadEstTermine(true);
    }

    private void resetScores(){
        equipeGauche.setScore(0);
        equipeDroite.setScore(0);
    }

    /**
     * Remet à zéro les variable du jeu
     */
    private void resetJeuDeFoot() {
        resetScores();
        ballonDuJeu.initBallon();
        initBoolsJeuDeFoot();
    }


/******************************  GETTER/SETTERS  ******************************/


    /**
     * Retourne la liste de tous les éléments qui bougent sur le terrain (sauf le ballon)
     * @return retourne une liste d'éléments mobiles
     */
    public ArrayList<Joueur> getJoueurs(){
        
        //on concatène les deux listes
        ArrayList<Joueur> listeElementsModbiles = new ArrayList<Joueur>(equipeGauche.getListeJoueurs());
        listeElementsModbiles.addAll(equipeDroite.getListeJoueurs());

        return listeElementsModbiles;

    }

    public Equipe getEquipeGauche() {return equipeGauche;}
    public Equipe getEquipeDroite() {return equipeDroite;}

    public Terrain getUnTerrain() {return unTerrain;}
    public Ballon getUnBallon() {return ballonDuJeu;}

    public void setPartieTerminee(boolean partieEncours) {PARTIEENCOURS = partieEncours;}
    public static boolean isPartieEnCours() {return PARTIEENCOURS;}

    /**
     * Retourne le temps écoulé depuis le début du match
     * @return retourne un long qui correspond au temps de match
     */
    public long getTempsEcoule(){
        return  (System.currentTimeMillis() - chronometre)/1000;
    }
  
}
