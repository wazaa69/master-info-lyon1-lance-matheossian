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
        this.estEnpause = true;

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

        while(true){

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


    public void deplacementVersballon(){

        angle = getAngleSelonBallon();

        avancer();
    }


    /**
     * Le joueur se déplace aléatoirement sur une distance
     */
    public void deplacementAuHasard()
    {
        int rotation = (int)(Math.random() * 30);

        if(Math.random() > 0.5)
            angle = (angle + rotation)%360;
        else angle = (angle - rotation)%360;

        avancer();
    }

   /**
    * Fait avancer le joueur sur la feuille de dessin, si il atteint un bord,
    * il fait demi-tour et s'avance.
    */

    public void avancer(){avancerAvecEssais(5);}

    /**
     * Fait avancer le joueur sur la feuille de dessin, si le prochain déplacement atteint un bord,
     * il fait demi-tour et s'avance (si possible).
     * @param nbEssais nombre d'essaie pour se déplacer
     */
    public void avancerAvecEssais(int nbEssais){

        if(nbEssais > 0){
        
            Point nouveauPoint = coordApresDep(x,y,caracteristiques.getDistDep(),angle);

            boolean bonEmplacement = isEmplacementValide(nouveauPoint);
            boolean pasDeContact = isValideDistContact(nouveauPoint);

            if (bonEmplacement && pasDeContact){ //1 1
                setXY(nouveauPoint);
                notifierObservateur(); //demande de rafaichissement de la vue des joueurs
            }

            else if(!bonEmplacement){ //0 1 ou 0 0
                angle = (angle + 180) % 360; //demi-tour
                avancerAvecEssais(nbEssais-1); //teste nouveau déplacement
            }

            else avancerAvecEssais(nbEssais-1); //1 0 
        }
        
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

    

/******************************  GETTER/SETTERS  ******************************/

    /**
     * Retourne l'angle pour que le joueur se dirige vers le ballon
     * @return retourne un entier correspondant à l'angle
     */
    protected int getAngleSelonBallon(){

        //calcul des différnces de coordonnées polaires
        float diffX = ballonDuJeu.getX() - x;
        float diffY = ballonDuJeu.getY() - y;

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

    public String getNom() {return nom;}

    public Caracteristiques getCaracteristiques() {return caracteristiques;}

    public Ballon getBallonDuJeu() {return ballonDuJeu;}

    public Equipe getMonEquipe() {return monEquipe;}


    /**
     * "Active/Désactive" le joueur
     */
    public void setEstEnpause() {
        this.estEnpause = !estEnpause;
        synchronized(this){notify();}
    }

}
