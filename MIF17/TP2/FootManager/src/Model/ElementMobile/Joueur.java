package Model.ElementMobile;

import Model.Equipe;
import Model.Terrain.Cage;
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
    protected Equipe equipeAdverse; /** l'équipe advese */

    protected boolean estEnpause; /** vais si le joueur est en pause, faux sinon */
    protected boolean threadEstTermine; /** vrai, pour terminer le thread, faux sinon */


    protected Point positionFormation; /** position attribuée par rapport à la formation choisit */
    protected boolean enCoursInterc; /** vrai, si le joueur est en train d'essayer d'intercepter le ballon, faux sinon */
    protected int tentatIntercep; /** le nombre de tentative restantes pour essayer d'intercepter le ballon*/


    

/*******************************  CONSTRUCTEUR  *******************************/

    /**
     * Initialise un joueur en lui donnant un nom, une Equipe et la connaissance de l'équipe adverse
     * @param nom le nom du joueur
     * @param ballonDuJeu le ballon du jeu en cours
     * @param monEquipe l'équipe du joueur
     * @param equipeAdverse l'équipe adverse
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
     * @param monEquipe l'équipe du joueur
     * @param equipeAdverse l'équipe adverse
     */
    public Joueur(int x, int y, String nom, Ballon ballonDuJeu, Equipe monEquipe, Equipe equipeAdverse) {

        super();
        
        this.x = x;
        this.y = y;

        initSimpleJoueur(nom, ballonDuJeu, monEquipe, equipeAdverse);
        
    }
    

    /**
     * Initialise un joueur avec un nom, un ballon, son Equipe et la connaissance de l'équipe adverse
     * @param nom le nom du joueur
     * @param ballonDuJeu le ballon du jeu en cours
     * @param monEquipe l'équipe du joueur
     * @param equipeAdverse l'équipe adverse
     */
    private void initSimpleJoueur(String nom, Ballon ballonDuJeu, Equipe monEquipe, Equipe equipeAdverse){
        this.nom = nom;
        initCaracteristiques();
        this.ballonDuJeu = ballonDuJeu;
        this.monEquipe = monEquipe;
        this.equipeAdverse = equipeAdverse;
        
        estEnpause = true;
        threadEstTermine = false;

        positionFormation = null;
        enCoursInterc = false;
        tentatIntercep = 3;

    }


    /**
     * Initialisation des caractéristiques
     */
    private void initCaracteristiques(){
        caracteristiques = new Caracteristiques();
        caracteristiques.setDistMinPrendreBalle(distanceMinContact*2);
        caracteristiques.setDistMaxTir(70);
        caracteristiques.setDistDep(1);
    }



/**********************************  THREAD  **********************************/


    @Override
    public void run() {demarrerJoueur();}


    /**
     * Boucle qui démarre et fait jouer le joueur
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
     * Le joueur se déplace aléatoirement d'un angle de +/-30°
     */
    public void avancerAuHasard()
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
    public void deplacementVersballon(){
        setAngleSelonBallon();
        avancer();
    }


    /**
     * Essayer d'avancer en faisant plusieurs essais
     */
    public void avancer(){

        int nbEssais = 40; //Important ! nbEssais <= 180 essais <=> 180°, 1 essai par degré
        int rotationParEssai = 180/nbEssais;

        //Rotation à gauche ou à droite
        if(Math.random() < 0.5) rotationParEssai = -rotationParEssai;

        avancerAvecEssais(nbEssais, rotationParEssai);
    }

    
    /**
     * Fait avancer le joueur sur la feuille de dessin, si le prochain déplacement atteint un bord,
     * il fait demi-tour et s'avance (si possible).
     */
    public void avancerAvecEssais(int nbEssais, int rotationParEssai){

        //Cette ligne est là, juste pour tester l'affichage.
        //notifierObserveur(); //Ne pas activer sinon (sa sert à rien).

        Point nouveauPoint = coordApresDep(x,y,caracteristiques.getDistDep(),angle);

        boolean bonEmplacement = isEmplacementValide(nouveauPoint);
        boolean pasDeContact = isValideDistCollision(nouveauPoint);


        if (nbEssais <= 0)
        {
            //cas d'arrêt : ne rien faire
        }

        else if (bonEmplacement && pasDeContact){ //1 1
            setXY(nouveauPoint);
            if(this == ballonDuJeu.getPossesseur()) ballonDuJeu.majXY();
            notifierObserveur(); //demande de rafaichissement de la vue des joueurs
        }

        else if(!bonEmplacement){ //0 1 ou 0 0
            angle = (angle + 180) % 360; //demi-tour
            avancerAvecEssais(nbEssais - 1, rotationParEssai); //teste nouveau déplacement
        }


        else{ //1 0 teste vers un nouvelle angle
            angle += rotationParEssai;
            avancerAvecEssais(nbEssais - 1, rotationParEssai);
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
     * Teste si le point ne provoque pas une collision avec un joueur
     * @param unPoint coordonnée à tester
     * @return retourne vrai si il ny a pas de collision, faux sinon
     */
    public boolean isValideDistCollision(Point unPoint){

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

/*********************************  METHODES *********************************/

    /**
     * Choisit au hasard une coordonnée dans la cage.
     * @return reourne le point ù le ballon va se rendre
     */
    public Point preparerTirPrMarquer(){

        Cage cageAdverse = equipeAdverse.getCage();

        //choix de tir dans la cage adverse
        int yTir = (int) (Math.random()*cageAdverse.getLargeur()) + (int) cageAdverse.getCoordonnees().getY();

        return new Point((int) cageAdverse.getCoordonnees().getX(), yTir);
    }


    /**
     * Si le joueur à le ballon, il cherche le coéquipier le plus éloigné mais
     * qui reste atteignable en une passe.
     * @return retourne le joueur qui peut recevoir le ballon
     */
    public Joueur passeAUnCoequipier(){

        if(this == ballonDuJeu.getPossesseur()){

            Cage cageAdverse = equipeAdverse.getCage();

            int distanceMaxDeTir = caracteristiques.getDistMaxTir();
            int distanceMaxtrouve = 0;
            int distanceEntreDeux = 0;

            boolean estEnAvant = false;

            Joueur unJoueur = null;
            Joueur unJoueurPasse = null;

            ArrayList<Joueur> listeJoueurs = monEquipe.getListeJoueurs();

                for(int i = 1; i < listeJoueurs.size(); i++){

                    unJoueur = listeJoueurs.get(i);
                    distanceEntreDeux = (int) getXY().distance(unJoueur.getXY());

                    
                    if((cageAdverse.getCoordonnees().getX() < x  && unJoueur.getX() < x) //Ennemis = à gauche
                            || (cageAdverse.getCoordonnees().getX() > x  && unJoueur.getX() > x)) //Ennemis = à droite
                        estEnAvant = true;

                    if(this != unJoueur 
                            && distanceEntreDeux <= distanceMaxDeTir
                            && distanceMaxtrouve < distanceEntreDeux
                            && estEnAvant){

                        unJoueurPasse = listeJoueurs.get(i);
                        distanceMaxtrouve = distanceEntreDeux;
                        
                    }

                    estEnAvant = false;
                }

            return unJoueurPasse;
        }

        else return null;
    }


/*************************  GETTER/SETTERS  AVANCEES *************************/


    /**
     * Retourne l'angle pour se diriger vers le point
     * @param unPoint un point
     * @return retourne un entier correspondant à l'angle
     */
    public int getAngleSelon(Point unPoint){

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
     * Met à jour l'angle, pour que le joueru se dirige un point
     * @param unPoint la destination
     */
    public void setAngleSelon(Point unPoint){
        angle = getAngleSelon(unPoint);
    }

    /**
     * Met à jour l'angle, pour que le joueur se dirige vers le ballon
     * @return retourne un entier correspondant à l'angle
     */
    public void setAngleSelonBallon(){
        angle = getAngleSelon(ballonDuJeu.getXY());
    }


    /**
     * Fais rentrer ou sortir un joueur de sa pause
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

    public int getTentatIntercep() {return tentatIntercep;}

    public boolean isEnCoursInterc() {return enCoursInterc;}

    public Point getPositionFormation() {return positionFormation;}




    public void setPositionFormation(Point positionFormation) {this.positionFormation = positionFormation;}

    public void setEnCoursInterc(boolean enCoursInterc) {this.enCoursInterc = enCoursInterc;}

    public void setTentatIntercep(int tentatIntercep) {this.tentatIntercep = tentatIntercep;}

    /**
     * Permet de terminer le thread ou de préparer son nouveau départ.
     */
    public void setThreadEstTermine(boolean threadEstTermine) {this.threadEstTermine = threadEstTermine;}

    /**
     * @return vrai si le thread est terminé, faux sinon
     */
    public boolean getThreadEstTermine() {return threadEstTermine;}

 
}