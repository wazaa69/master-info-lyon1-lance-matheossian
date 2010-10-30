package Model.ElementMobile;

import Model.Equipe;
import Model.Terrain.Terrain;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Un joueur appartenant à une équipe
 */
public class Joueur extends ElementMobile {


    protected String nom; /** nom du joueur */
    protected Caracteristiques caracteristiques; /** les caractéristiques  */

    protected Ballon ballonDuJeu; /** le ballon du jeu de foot en cours */
    protected Equipe monEquipe; /**  l'équipe du joueur */
    protected Equipe equipeAdverse; /** equipe advese */

    protected boolean estEnpause; /** le joueur est en pause */
    protected boolean threadEstTermine; /** pour terminer le thread */


  

/*******************************  CONSTRUCTEUR  *******************************/

    /**
     * Initialise un joueur en lui donnant un nom, une Equipe et la connaissance de l'équipe adverse
     * @param nom le nom du joueur
     * @param ballonDuJeu le ballon du jeu en cours
     * @param monEquipe équipe du joueur
     * @param equipeAdverse équipe adverse
     */
    public Joueur(String nom, Ballon ballonDuJeu, Equipe monEquipe, Equipe equipeAdverse) {
        super();
        initSimpleJoueur(nom, ballonDuJeu, monEquipe, equipeAdverse);
    }


    /**
     * Initialise un joueur
     * @param x coordonnées polaires
     * @param y coordonnées polaires
     * @param nom le nom du joueur
     * @param ballonDuJeu le ballon du jeu en cours
     * @param monEquipe équipe du joueur
     * @param equipeAdverse équipe adverse
     */
    public Joueur(int x, int y, String nom, Ballon ballonDuJeu, Equipe monEquipe, Equipe equipeAdverse) {

        super();
        
        this.x = x;
        this.y = y;

        initSimpleJoueur(nom, ballonDuJeu, monEquipe, equipeAdverse);
        
    }
    

    /**
     * Initialise un joueur avec un nom, son Equipe et la connaissance de l'équipe adverse
     * @param nom le nom du joueur
     * @param monEquipe l'équipe du joueur
     * @param equipeAdverse equipe adverse
     */
    private void initSimpleJoueur(String nom, Ballon ballonDuJeu, Equipe monEquipe, Equipe equipeAdverse){
        this.nom = nom;
        this.ballonDuJeu = ballonDuJeu;
        this.monEquipe = monEquipe;
        this.equipeAdverse = equipeAdverse;
        estEnpause = true;
        threadEstTermine = false;

        setCaracteristiques();

    }


    public void setCaracteristiques(){
        caracteristiques = new Caracteristiques();
        caracteristiques.setDistMinPrendreBalle(distanceMinContact);
        caracteristiques.setDistDep(1);
    }



/**********************************  THREAD  **********************************/


    @Override
    public void run() {demarrerJoueur();}


    /**
     * Boucle qui fait jouer le joueur
     */
    public void demarrerJoueur(){

        //System.out.println(nom + " - Lancement du Thread joueur : demarrerJoueur()");

        estEnpause = false;

        while(!threadEstTermine){

            if(estEnpause){
                try {
                    synchronized(this){wait();}
                } catch (InterruptedException ex) {
                    Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //utilise la stratégie de l'équipe
            monEquipe.getStartegie().utiliserStrat(this);
            
            try {
                sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
            }

 
        }
    }


/*******************************  DEPLACEMENT  *******************************/

    /**
     * Le joueur se déplace aléatoirement
     */
    public void deplacementAuHasard()
    {
        int rotation = (int)(Math.random() * 30);
        int tmpAngle;

        if(Math.random() > 0.5)
            angle = (angle + rotation)%360;
        else angle = (angle - rotation)%360;

        avancer();
    }


    /**
     * Rapproche le joueur du ballon
     */
    public void deplacementVers(ElementMobile unElementMobile){
        setAngleSelon(unElementMobile.getXY());
        avancer();
    }
    
    /**
     * Rapproche le joueur du ballon
     */
    public void deplacementVersballon(){
        setAngleSelonBallon();
        avancer();
    }




    
    /**
     * Fait avancer le joueur sur la feuille de dessin, si le prochain déplacement atteint un bord,
     * il fait demi-tour et s'avance (si possible).
     */
    public void avancer(){

        //Cette ligne est là, juste pour tester l'affichage.
        //notifierObserveur(); //Ne pas activer sinon (sa sert à rien).

        Point nouveauPoint = coordApresDep(x,y,caracteristiques.getDistDep(),angle);

        boolean bonEmplacement = isEmplacementValide(nouveauPoint);
        boolean pasDeContact = isValideDistContact(nouveauPoint);

        if (bonEmplacement && pasDeContact){ //1 1
            setXY(nouveauPoint);
            notifierObserveur(); //demande de rafaichissement de la vue des joueurs
        }

        else if(!bonEmplacement){ //0 1 ou 0 0
            angle = (angle + 180) % 360; //demi-tour
            avancer(); //teste nouveau déplacement
        }

        //1 0 sinon ne rien faire

        
    }


/******************************  VERIFICATIONS  ******************************/

     /*
     * Teste si le point ne sort pas de la feuille de dessin
     * @param unPoint coordonnée à tester
     * @return retourne vrai si l'emplacement est valide, faux sinon
     */
    protected boolean isEmplacementValide(Point unPoint){

        Dimension dim =  Terrain.getDimTerrain();

        if(( unPoint.getX() < Terrain.MARGESEINTE  || unPoint.getX() > (dim.getWidth() + Terrain.MARGESEINTE))
        || ( unPoint.getY() < Terrain.MARGESEINTE  || unPoint.getY() > (dim.getHeight() + Terrain.MARGESEINTE)))
            return false;

        return true;
    }


    /**
     * Teste si le point n'est pas à l'intérieur du rayon de contact d'un joueur
     * @param unPoint coordonnée à tester
     * @return
     */
    public boolean isValideDistContact(Point unPoint){

        //on récupère chaque équipe
        ArrayList<Joueur> listeJoueurEquUne = monEquipe.getListeJoueurs();
        ArrayList<Joueur> listeJoueurEquDeux = equipeAdverse.getListeJoueurs();

        //on concatène les deux listes
        ArrayList<ElementMobile> listeJoueurs = new ArrayList<ElementMobile>(listeJoueurEquUne);
        listeJoueurs.addAll(listeJoueurEquDeux);

        for(int i = 0; i < listeJoueurs.size(); i++)
            if(listeJoueurs.get(i) != this)
                if(!isBonneDistance(unPoint, listeJoueurs.get(i)))
                    return false; //distance non respectée

        return true;

    }

    

/*************************  GETTER/SETTERS  AVANCEES *************************/

    /**
     * Retourne l'angle pour se diriger vers le point
     * @param unPoint un point
     * @return retourne un entier correspondant à l'angle
     */
    private int getAngleSelon(Point unPoint){

        //calcul des différnces de coordonnées polaires
        float diffX = (float) unPoint.getX() - x;
        float diffY = (float) unPoint.getY() - y;

        float differentZero = (float) 0.01;

        //côté opposé sur adjancent
        float oppSurAdj = diffY/(diffX + differentZero);

        //tan-1(opp/adj)
        int unAngle = (int) Math.toDegrees(Math.atan(oppSurAdj));
        

        if(diffX >= 0)
            return unAngle; //si le joueur actuel est à gauche de la balle
        else
            return 180 + unAngle; //sinon il est à droite

    }

    /**
     * Permet de diriger le joueur vers un points
     * @param unPoint la destination
     */
    public void setAngleSelon(Point unPoint){
        angle = getAngleSelon(unPoint);
    }

    /**
     * Retourne l'angle pour que le joueur se dirige vers le ballon
     * @return retourne un entier correspondant à l'angle
     */
    public void setAngleSelonBallon(){
        angle = getAngleSelon(ballonDuJeu.getXY());
    }

//    /**
//     * Cette classe permet de d'effectuer une rotation en plusieurs parties :
//     * du point de départ jusqu'à l'arrivé, le joueur suit une courbe.
//     * @param unElementMobile un élément mobile
//     */
//    public void setAnglePrAllerVers(Point pointDestination){
//
//        //si le point n'a pas bougé, l'angle et les étapes restent les mêmes
//        if(destination != null && destination.equals(pointDestination) && compterEtapes <= nbEtapesJusqPoint){
//            angle += angleAeffectuer/nbEtapesJusqPoint; //ajout d'une partie de l'angle à effectuer
//            compterEtapes++;
//        }
//        else if (compterEtapes > nbEtapesJusqPoint) { //sinon, on remet tout à jour
//            destination = pointDestination; //sauvegarde de la nouvelle destination
//            angleAeffectuer = getAngleSelon(destination); //l'angle de rotation complet à effectuer
//            int distance = (int) (new Point(x,y)).distance(pointDestination.getX(),pointDestination.getY());
//            nbEtapesJusqPoint = (distance - distanceMinContact)/caracteristiques.getDistDep(); //environ
//        }
//
//    }


    /**
     * "Active/Désactive" le joueur
     */
    public void setEstEnpause() {
        this.estEnpause = !estEnpause;
        synchronized(this){notify();}
    }

    
/*******************************  GETTER/SETTERS ******************************/


    public String getNom() {return nom;}

    public Caracteristiques getCaracteristiques() {return caracteristiques;}

    public Ballon getBallonDuJeu() {return ballonDuJeu;}

    public Equipe getMonEquipe() {return monEquipe;}

    public Equipe getEquipeAdverse() {return equipeAdverse;}



    /**
     * Permet de terminer le thread ou de préparer son nouveau départ.
     */
    public void setThreadEstTermine(boolean threadEstTermine) {this.threadEstTermine = threadEstTermine;}

    /**
     * @return vrai si le thread est terminé, faux sinon
     */
    public boolean getThreadEstTermine() {return threadEstTermine;}
 
}