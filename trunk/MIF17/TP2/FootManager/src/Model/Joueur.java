package Model;

import Model.Strategies.Strategie;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Un joueur appartenant à une équipe
 */
public class Joueur extends ElementMobile {


    private String nom; /** nom du joueur */
    private Equipe monEquipe; /**  l'équipe du joueur */
    private Equipe equipeAdverse; /** equipe advese */

    private Caracteristiques caracteristiques; /** les caractéristiques du joueur */

    private boolean estEnpause; /** le joueur est en pause café */

    
    /**
     * Initialise un joueur avec des coordonnées, un nom et une Equipe
     * @param x coordonnées polaires
     * @param y coordonnées polaires
     * @param nom le nom du joueur
     * @param monEquipe l'équipe du joueur
     * @param equipeAdverse equipe adverse
     */
    public Joueur(int x, int y, int angle, String nom, Equipe monEquipe, Equipe equipeAdverse) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        
        this.nom = nom;
        this.monEquipe = monEquipe;
        this.equipeAdverse = equipeAdverse;

        this.estEnpause = true;
        
    }
    

    /**
     * Initialise un joueur avec un nom et son Equipe
     * @param nom le nom du joueur
     * @param monEquipe l'équipe du joueur
     * @param equipeAdverse equipe adverse
     */
    public Joueur(String nom, Equipe monEquipe, Equipe equipeAdverse) {
        this.nom = nom;
        this.monEquipe = monEquipe;
        this.equipeAdverse = equipeAdverse;
        this.estEnpause = true;
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
                    synchronized(this){wait();} //aller boire un café
                } catch (InterruptedException ex) {
                    Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            switch(monEquipe.getStartegie()){
                case 0: //neutre
                    deplacementAuHasard(1);
                    break;
                case 1: //defense
                    break;
                case 2: //attaque
                    break;
            }
            
            try {
                sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
            }

 
        }
    }


/*******************************  DEPLACEMENT  *******************************/


    protected void deplacement(){



        

    }


    /**
     * Le joueur se déplace aléatoirement sur une distance
     * @param distance la distance à parcourir
     */
    public void deplacementAuHasard(int distance)
    {
        int rotation = (int)(Math.random() * 30);
        
        if(Math.random() > 0.5)
            angle = (angle + rotation)%360;
        else angle = (angle - rotation)%360;
        
        avancer(distance);
    }

   /**
    * Fait avancer le joueur sur la feuille de dessin, si il atteint un bord,
    * il fait demi-tour et s'avance.
    * @param distance la distance à parcourir
    */
    @Override
    public void avancer(int distance){avancerAvecEssais(distance,5);}

    /**
     * Fait avancer le joueur sur la feuille de dessin, si le prochain déplacement atteint un bord,
     * il fait demi-tour et s'avance (si possible).
     * @param distance la distance à parcourir
     * @param nbEssais nombre d'essaie pour se déplacer
     */
    public void avancerAvecEssais(int distance, int nbEssais){

        if(nbEssais > 0){
        
            Point nouveauPoint = coordonneesDeplacement(x,y,distance,angle);

            boolean bonEmplacement = isEmplacementValide(nouveauPoint);
            boolean pasDeContact = isValideDistContact(nouveauPoint);

            if (bonEmplacement && pasDeContact){ //1 1
                setXY(nouveauPoint);
                notifierObservateur(); //demande de rafaichissement de la vue des joueurs
            }

            else if(!bonEmplacement){ //0 1 ou 0 0
                angle = (angle + 180) % 360; //demi-tour
                avancerAvecEssais(distance, nbEssais-1); //teste nouveau déplacement
            }

            else avancerAvecEssais(distance, nbEssais-1); //1 0
            
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
        ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>(listeJoueurEquUne);
        listeJoueurs.addAll(listeJoueurEquDeux);

        for(int i = 0; i < listeJoueurs.size(); i++)
            if(listeJoueurs.get(i) != this)
                if(!isBonneDistance(unPoint, listeJoueurs.get(i)))
                    return false; //distance non respectée

        return true;

    }

    

/***************************  COORDONNEES ET ANGLE  ***************************/

    /**
     * Calcul des nouvelles coordonnées selon un point de départ, une distance et un angle
     * @param x point de départ en abscisse
     * @param y point de départ en ordonnée
     * @param distance la distance à parcourir
     * @param angle l'angle
     * @return retourne la position du nouveau point
     */
    private Point coordonneesDeplacement(int x, int y, int distance, int angle){

        Point point = new Point();

        point.setLocation(
                Math.round(x + distance*Math.cos(convDegGrad*angle)),
                Math.round(y + distance*Math.sin(convDegGrad*angle))
                );

        return point;

    }

    /**
     * Retourne l'angle où le ballon peut être vu, depuis la position du joueur
     * @return retourne un entier correspondant à l'angle
     */
    protected int setAngleSelonBallon(){

        //calcul des différnces de coordonnées polaires
        int diffX = JeuDeFoot.UNBALLON.getX() - x;
        int diffY = JeuDeFoot.UNBALLON.getY() - y;

        //côté adjancent sur opposé
        int adjSurOpp = Math.round( diffX/(diffY+1) );

        //System.out.println(Math.toDegrees(Math.atan(adjSurOpp)));

        //tan-1(adj/opp)
        return (int) Math.toDegrees(Math.atan(adjSurOpp));
    }

/******************************  GETTER/SETTERS  ******************************/


     /**
     * @return Retourne le nom du joueur
     */
    public String getNom() {return nom;}

    public Equipe getMonEquipe() {return monEquipe;}

    public void setXY(Point unPoint) {
         x = (int) unPoint.getX();
         y = (int) unPoint.getY();
    }

    /**
     * Réactive le thread si il était en pause
     */
    public void setEstEnpause() {
        this.estEnpause = !estEnpause;
        synchronized(this){notify();}
    }

}
