package Model;

import Model.ElementMobile.Ballon;
import Model.Terrain.Terrain;
import Model.ElementMobile.Joueur;
import Model.ElementMobile.JoueurGoal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 * La classe qui gère un jeu de foot
 */
public class JeuDeFoot extends Thread {


    private long horlogeDebutMatch; /**  horloge pour gérer le temps de jeu */

    private Equipe equipeGauche; /**   équipe à gauche sur le terrain */
    private Equipe equipeDroite; /**   équipe à droite sur le terrain */

    private Terrain unTerrain; /**  le terrain de jeu */

    public  Ballon ballonDuJeu; /**  le ballon de foot */

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

        ballonDuJeu = new Ballon(Math.round(longueurTerrain/2), Math.round(largeurTerrain/2), 5, Color.MAGENTA);

        initEquipes(nbJoueursParEq);

        PARTIEENCOURS = false;
        pauseRepartir = false;

    }


/***********************   Initialisation des Equipes  ************************/


    private void initEquipes(int nbJoueurs){
        
        equipeGauche = new Equipe("RedTeam",Color.BLUE, unTerrain.getCageGauche());
        equipeDroite = new Equipe("BlueTeam",Color.RED, unTerrain.getCageDroite());

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

        //Création du goal
        nom = courante.getNomEquipe() + " - Goal";
        unJoueur = new JoueurGoal(nom, ballonDuJeu, courante, adverse);
        courante.ajouterUnJoueur(unJoueur);

        //création du reste de l'équipe, on commence à 1
        for(int i = 1; i < nbJoueurs; i++){

            while(unPoint == null) {unPoint = pointHasardDsTerrain();}

            nom = courante.getNomEquipe() + " - " + (i + 1);

            //création du joueur
            unJoueur = new Joueur((int) unPoint.getX(), (int) unPoint.getY(), nom, ballonDuJeu, courante, adverse);

            courante.ajouterUnJoueur(unJoueur);
            
            unPoint = null;
            
        }

    }

/***********************   Placement  ********/

    /**
     * Recherche un point et une zone au hasard sur le terrain,
     * qui n'est pas déjà occupé par un autre joueur.
     * @return retourne le point libre si il est dispponible, null sinon
     */
    private Point pointHasardDsTerrain(){


        //on concatène les deux listes
        ArrayList<Joueur> listeJoueurs = getJoueurs();

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

        PARTIEENCOURS = true;

        //initialisation de l'horloge, à partir de l'heure actuelle (en millisecondes)
        horlogeDebutMatch = System.currentTimeMillis();

        ArrayList<Joueur> listeJoueurEquUne = equipeGauche.getListeJoueurs();
        ArrayList<Joueur> listeJoueurEquDeux = equipeDroite.getListeJoueurs();

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

        ArrayList<Joueur> listeJoueurEquUne = equipeGauche.getListeJoueurs();
        ArrayList<Joueur> listeJoueurEquDeux = equipeDroite.getListeJoueurs();



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
        PARTIEENCOURS = false;
        pauseRepartir = false;
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
    public long tempsEcoule(){
        return horlogeDebutMatch - System.currentTimeMillis();
    }
  
}
