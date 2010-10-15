package Model;

import ObservListe.Observateur;

public class Joueur extends ElementMobile {


    private String nom; /** nom du joueur */

    private Equipe monEquipe; /** l'équipe du joueur */


    /**
     * Initialise un joueur avec un nom et son Equipe
     * @param nom le nom du joueur
     * @param monEquipe l'équipe du joueur
     */
    public Joueur(String nom, Equipe monEquipe) {
        initPosition();
        this.nom = nom;
        this.monEquipe = monEquipe;
    }

    /**
     * Initialise un joueur avec des coordonnées, un nom et une Equipe
     * @param x coordonnées polaires
     * @param y coordonnées polaires
     * @param nom le nom du joueur
     * @param monEquipe l'équipe du joueur
     */
    public Joueur(int x, int y, String nom, Equipe monEquipe) {
        this.x = x;
        this.y = y;
        this.nom = nom;
        this.monEquipe = monEquipe;
    }


    /**
     * Initialise la position du joueur
     */
    private void initPosition(){
        x = 0;
        y = 0;
    }


    @Override
    public void run() {demarrerJoueur();}

    /**
     * Boucle qui fait jouer le joueur
     */
    public void demarrerJoueur(){

            //System.out.println(nom + " - Lancement du Thread joueur : demarrerJoueur()");
            /*
            while(true){
            //notifier
            }
             */

        /*
        try {
            wait(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
         */

        notifierObservateur();
    }


     /**
     * @return Retourne le nom du joueur
     */
    public String getNom() {return nom;}

    public Equipe getMonEquipe() {return monEquipe;}



    /**************************
     *  Méthodes de l'observé
     **************************/

    public void ajouterObservateur(Observateur obs) {
        unObservateur = obs;
    }

    public void supprimerObservateur() {
        unObservateur = null;
    }

    public void notifierObservateur() {
        unObservateur.miseAJour();
    }

}
