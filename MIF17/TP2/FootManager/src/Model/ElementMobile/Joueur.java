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
    protected Equipe equipeAdverse; /** equipe advese */

    protected boolean estEnpause; /** le joueur est en pause */
    protected boolean threadEstTermine; /** pour terminer le thread */


    protected Point positionFormation; /** position attribuée par rapport à la formation choisit */
    protected boolean enCoursInterc; /** vrai, si le joueur est en train d'essayer d'intercepter le ballon, faux sinon */
    protected int tentatIntercep; /** le nombre de tentative restante pour essayer d'intercepter le ballon*/


    

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
        setCaracteristiques();
        this.ballonDuJeu = ballonDuJeu;
        this.monEquipe = monEquipe;
        this.equipeAdverse = equipeAdverse;
        
        estEnpause = true;
        threadEstTermine = false;

        positionFormation = null;
        enCoursInterc = false;
        tentatIntercep = 3;

    }


    public void setCaracteristiques(){
        caracteristiques = new Caracteristiques();
        caracteristiques.setDistMinPrendreBalle(distanceMinContact);
        caracteristiques.setDistMaxTir(70);
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
        boolean pasDeContact = isValideDistContact(nouveauPoint);


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

/*********************************  METHODES *********************************/

    /**
     * Calcul les coordonnées du ballon dans la cage, si le joueur tir
     * @return reourne le point ou le ballon va se rendre
     */
    public Point preparerTirPrMarquer(){

        Cage cageAdverse = equipeAdverse.getCage();

        //choix de tir dans la cage adverse
        int yTir = (int) (Math.random()*cageAdverse.getLargeur()) + (int) cageAdverse.getCoordonnees().getY();

        return new Point((int) cageAdverse.getCoordonnees().getX(), yTir);
    }


    /**
     * Si le joueur à le ballon, il va chercher le coéquipier le plus proche
     * des cages ennemis à qui il peut faire la passe
     * @return retourne le joueur qui peut recevoir le ballon
     */
    public Joueur passeAUnCoequipier(){

        if(this == ballonDuJeu.getPossesseur()){

            Cage cageAdverse = equipeAdverse.getCage();

            int distanceMaxDeTir = caracteristiques.getDistMaxTir();
            int distanceMaxtrouve = 0;
            int distanceEntreDeux = 0;

            Joueur unJoueur = null;
            Joueur unJoueurPasse = null;

            ArrayList<Joueur> listeJoueurs = monEquipe.getListeJoueurs();

            if(cageAdverse.getCoordonnees().getX() < x){//Ennemis = à gauche

                for(int i = 1; i < listeJoueurs.size(); i++){

                    unJoueur = listeJoueurs.get(i);
                    distanceEntreDeux = (int) getXY().distance(unJoueur.getXY());

                    if(this != unJoueur 
                            && distanceEntreDeux <= distanceMaxDeTir
                            && distanceMaxtrouve < distanceEntreDeux){

                        unJoueurPasse = listeJoueurs.get(i);
                        distanceMaxtrouve = distanceEntreDeux;
                        
                    }
                }

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