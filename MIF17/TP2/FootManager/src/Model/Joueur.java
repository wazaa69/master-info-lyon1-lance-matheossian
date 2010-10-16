package Model;

import ObservListe.Observateur;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Joueur extends ElementMobile {


    private String nom; /** nom du joueur */

    private Equipe monEquipe; /** l'équipe du joueur */


    /**
     * Initialise un joueur avec un nom et son Equipe
     * @param nom le nom du joueur
     * @param monEquipe l'équipe du joueur
     */
    public Joueur(String nom, Equipe monEquipe) {
        x = 0;
        y = 0;
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


/**********************************  THREAD  **********************************/


    @Override
    public void run() {demarrerJoueur();}


    /**
     * Boucle qui fait jouer le joueur
     */
    public void demarrerJoueur(){

        //System.out.println(nom + " - Lancement du Thread joueur : demarrerJoueur()");

        while(true){

            deplacementAuHasard(5);
            notifierObservateur();

            
            try {
                sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
            }

 
        }
    }


/*******************************  DEPLACEMENT  *******************************/

    
    /**
     * Le joueur se déplace aléatoirement sur une distance
     * @param dist la distance à parcourir
     */
    public void deplacementAuHasard(int distance)
    {
        int rotation = (int)(Math.random() * 45);
        
        if(Math.random() > 0.5)
            angle = (angle + rotation)%360;
        else angle = (angle - rotation)%360;
        
        avancer(distance);
    }

   /**
    * Fait avancer la tortue sur la feuille de dessin, si elle atteint un bord,
    * elle fait demi-tour et s'avance.
    * @param dist la distance à parcourir
    */
    @Override
    public void avancer(int distance)
    {

        Point nouveauPoint = coordonneesSelonAngle(x,y,distance,angle);

        if (emplacementValide(nouveauPoint))
        {
            x = (int) nouveauPoint.getX();
            y = (int) nouveauPoint.getY();
        }
        else { //demi-tour

            angle = (angle + 180) % 360;

            //on recalcul avec le nouvel angle
            nouveauPoint.setLocation(coordonneesSelonAngle(x,y,distance,angle));

            x = (int) nouveauPoint.getX();
            y = (int) nouveauPoint.getY();
        }

    }


/******************************  VERIFICATIONS  ******************************/

     /*
     * Teste si le joueur ne sort pas de la feuille de dessin
     * @param x coordonnée en abscisse à tester
     * @param y coordonnée en ordonné à tester
     * @return retourne vrai si l'emplacement est valide, faux sinon
     */
    protected boolean emplacementValide(Point point){

        int longueurTerrain = Terrain.LONGUEUR - Terrain.BORDUREINTE;
        int largeurTerrain = Terrain.LARGEUR - Terrain.BORDUREINTE;
        
        if(( point.getX() <= Terrain.BORDUREINTE ) || (point.getX() >= longueurTerrain )
        || ( point.getY() <= Terrain.BORDUREINTE ) || (point.getY() >= largeurTerrain))
            return false;

        return true;
    }


    

/*******************************  COORDONNEES  *******************************/

    /**
     * Calcul des nouvelles coordonnées selon un point de départ, une distance et un angle
     * @param x point de départ en abscisse
     * @param y point de départ en ordonnée
     * @param distance la distance à parcourir
     * @param angle l'angle
     * @return retourne la position du nouveau point
     */
    private Point coordonneesSelonAngle(int x, int y, int distance, int angle){

        Point point = new Point();

        point.setLocation(
                Math.round(x + distance*Math.cos(convDegGrad*angle)),
                Math.round(y + distance*Math.sin(convDegGrad*angle))
                );

        return point;

    }


/******************************  GETTER/SETTERS  ******************************/


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
