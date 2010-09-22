package tortuesmanager;

import java.awt.*;


/* Un petit Logo minimal qui devra être amélioré par la suite
J. Ferber - 1999-2001 Cours de DESS TNI - Montpellier II - version 2.0 - date 25/09/2001
*/

/*
 * Une tortue qui se déplace en coordonnées polaires
 */
public class Tortue
{

     //######################################################################################################      ATTRIBUTS


    protected static final int rp = 10, rb = 5; /** pour le tracé de la tortue  */
    protected FeuilleDessin feuille;     /** la feuille de dessin sur laquelle dessine la tortue */


    private int x, y;	/** les coordonnees de la tortue */
    protected double convDegGrad = 0.0174533;     /** la constante de conversion de degres en gradient  */
    protected int dir;	/** la direction de la tortue */
    protected boolean crayon = true; /** si vrai alors le cayon est baissé, faux sinon */
    
    protected int traitCouleur; /** couleur du trait de la tortue courante */
    protected  Color tortueCouleur; /** Couleur de la tortue */


    protected final int distMinEntreTortues = 1; /** distance minimum entre deux tortues */


    //######################################################################################################      CONSTRUCTEURS

   /**
    * Constructeur
    * @param feuille la feuille de dessin
    * @param crayon si vrai alors le cayon est baiss
    */
    public Tortue(FeuilleDessin feuille, boolean crayon){
        reset();
        this.feuille = feuille;
        this.crayon = crayon;
       
        tortueCouleur = Color.BLUE;
        this.feuille.getListeTortues().add(this);
    }


    //######################################################################################################      ACCESSEURS

    /**
    * Retourne la couleur de la tortue
    */
    public Color getCouleur(){return tortueCouleur;}

    public int getX(){return x;}

    public int getY(){return y;}

    public int getDir(){return dir;}

    //######################################################################################################      MUTATEURS

    /**
    * Change la couleur de la tortue
    * @param n Nouvelle couleur pour la tortue
    */
    public void setCouleur(Color coul1){tortueCouleur = coul1;}

    /**
    * Modifie le booleen crayon qui détermine si la tortue laisse un trait en se déplaçant
    * @param b booleen 
    */
    public void setCrayon(boolean b){crayon = b;}

    public void setColor(int n){traitCouleur = n;}

    public void setX(int x) {this.x = x;}

    public void setY(int y) {this.y = y;}

    public void setDir(int dir) { this.dir = dir; }

    //######################################################################################################      METHODES


    //####################################################################################### M: PLACEMENT

   /**
    * Positionne la tortue en (250;180), vers le haut et avec le crayon levé
    */
    void reset()
    {
        x = 250;
        y = 180;
        dir = -90;
        crayon = true;
    }


    /*
     * Teste si la tortue ne sort pas de la feuille de dessin
     * @param x coordonnée en abscisse à tester
     * @param y coordonnée en ordonné à tester
     * @return retourne vrai si l'emplacement est valide, si ce n'est pas le cas,
     * la tortue fait un demi-tour et faux est renvoyé.
     *
     */
    public boolean emplacementValide(int newX, int newY){

        int largeurTerrain = feuille.drawingImage.getWidth(feuille)-10;
        int hauteurTerrain = feuille.drawingImage.getHeight(feuille)-10;

        int distBord = 20;
     
        if((newX <= distBord )|| (newX >= largeurTerrain )||( newY <= distBord )|| (newY >= hauteurTerrain))
        {
            dir = (dir + 180) % 360; //demi-tour
 
            return false;
        }
        return true;
    }

    /*--------------------------------------------------------*/
    /*  les procedures de base de fonctionnement de la tortue
    /*--------------------------------------------------------*/
    
   /**
    * Fait avancer la tortue sur la feuille de dessin
    * @param dist la distance à parcourir en pixel
    */
    public void avancer(int dist)
    {

        Graphics g = feuille.getImageGraphics();

        int newX = (int) Math.round(x + dist*Math.cos(convDegGrad*dir));
        int newY = (int) Math.round(y + dist*Math.sin(convDegGrad*dir));

       if (emplacementValide(newX, newY))
       {
            x = newX;
            y = newY;
       }
    
        if (crayon) {
            g.setColor(decodeColor(traitCouleur));
            g.drawLine(x,y,newX,newY);
        }
    }


    /**
     * La tortue se déplace aléatoirement
     * @param dist la distance à parcourire
     */
    public void deplacementAuHasard(int dist)
    {
        //Respect de la distance minimale ?
        int distMinimale = distMinEntreTortues;
        if(dist > distMinEntreTortues) distMinimale = dist;

        //déplacement aléatoire
        int angle = (int)(Math.random() * 90);
        if(Math.random() > 0.5) dir = (dir + angle)%360; else dir = (dir - angle)%360;
        avancer(distMinimale);
    }

    /**
    * Aller à droite
    * @param ang l'angle de rotation
    */
    public void droite(int ang)
    {
        dir = (dir + ang) % 360;
        feuille.drawIt();
    }

    /**
    * Aller à gauche
    * @param ang l'angle de rotation
    */
    public void gauche(int ang)
    {
        dir = (dir - ang) % 360;
        feuille.drawIt();
    }

    //####################################################################################### M: DIVERS



    //####################################################################################### M: DESSIN

    /**
    * Dessine la tortue
    * @param g le graphique (le support de dessin)
    */
    protected void dessinerTortue(Graphics g){

        //Calcule les 3 coins du triangle a partir de
        // la position de la tortue p
        Point p = new Point(getX(),getY());
        Polygon arrow = new Polygon();

        //Calcule des deux bases
        //Angle de la droite
        double theta = convDegGrad * (- dir);
        //Demi angle au sommet du triangle
        double alpha = Math.atan( (float) rb / (float)rp );
        //Rayon de la fleche
        double r = Math.sqrt( rp*rp + rb*rb );
        //Sens de la fleche

        //Pointe
        Point p2 = new Point((int) Math.round(p.getX()+r*Math.cos(theta)),
                                             (int) Math.round(p.y-r*Math.sin(theta)));
        arrow.addPoint(p2.x,p2.y);
        arrow.addPoint((int) Math.round( p2.getX()-r*Math.cos(theta + alpha) ),
              (int) Math.round( p2.y+r*Math.sin(theta + alpha) ));

        //Base2
        arrow.addPoint((int) Math.round( p2.x-r*Math.cos(theta - alpha) ),
              (int) Math.round( p2.y+r*Math.sin(theta - alpha) ));

        arrow.addPoint(p2.x,p2.y);
        g.setColor(getCouleur()); // on récupère la couleur définie dans tortue
        g.fillPolygon(arrow);
    }

    /**
    * Renvoie une couleur selon le code choisit
    * @param c un code couleur
    * @return une couleur de type Color
    */
    Color decodeColor(int c) {
        switch(c) {
                case 0: return(Color.black);
                case 1: return(Color.blue);
                case 2: return(Color.cyan);
                case 3: return(Color.darkGray);
                case 4: return(Color.red);
                case 5: return(Color.green);
                case 6: return(Color.lightGray);
                case 7: return(Color.magenta);
                case 8: return(Color.orange);
                case 9: return(Color.gray);
                case 10: return(Color.pink);
                case 11: return(Color.yellow);
                default : return(Color.black);
        }
    }

    /**
    *  Baisse le crayon pour dessiner
    */
    public void baisserCrayon(){crayon = true;}

    /**
     * lever le crayon pour ne plus dessiner
     */
    public void leverCrayon(){crayon = false;}

    /**
     * Attribut une nouvelle couleur au trait de dessin
     * @param n le code couleur entier positif
     */
    public void couleur(int n){traitCouleur = n % 12;}

    /**
     * Passer à la couleur suivante
     */
    public void couleurSuivante() {couleur(traitCouleur+1);}



    /**
    * Retourne la distance entre la tortue et un point (x,y)
    * @param x coordonnee en abscisse
    * @param y coordonnee en ordonnée
    * @return distance entre la tortue et le point
    */
    public int distPoint(int x, int y)
    {
       return (int) Math.round(Math.sqrt(Math.pow((x - this.x),2) + Math.pow((y - this.y),2)));
    }

    /**
    * Retourne la distance entre la tortue et un point (x,y)
    * @param x1 première coordonnee en abscisse
    * @param y1 première coordonnee en ordonnée
    * @param x2 seconde coordonnee en abscisse
    * @param y2 seconde coordonnee en ordonnée
    * @return distance entre la tortue et le point
    */
    public int distPoint(int x1, int y1, int x2, int y2)
    {
       return (int) Math.round(Math.sqrt(Math.pow((x2 - x1),2) + Math.pow((y2 - y1),2)));
    }


   /**
    * Calcul la distance entre deux tortues
    * @param tortue la deuxieme tortue
    * @return la distance entre les deux tortues
    */
   public int distTortue(Tortue tortue)
   {
       int coord_x = tortue.x;
       int coord_y = tortue.y;
       return distPoint(coord_x,coord_y);
   }

    /*-----------------------------------*/
    /*              FIGURES
    /*-----------------------------------*/


    /**
     * Dessine un carré
     */
    public void carre() {
        for (int i=0;i<4;i++) {
                avancer(100);
                droite(90);
        }
    }


    /**
     * Dessine un polygone
     */
    public void poly(int n, int a) {
        for (int j=0;j<a;j++) {
                avancer(n);
                droite(360/a);
        }
    }

    /**
    * Dessine une spirale
    */
    public void spiral(int n, int k, int a) {
        for (int i = 0; i < k; i++) {
                couleur(traitCouleur+1);
                avancer(n);
                droite(360/a);
                n = n+1;
        }
    }


    /*-----------------------IMMEUBLE----------------------------->*/

    /**
    * Dessine une fenêtre
    */
    public void window(){
        for(int i = 0; i < 2; i++){
            avancer(7);
            droite(90);
            avancer(5);
            droite(90);
        }
    }


     /**
     * Décalage du crayon
     * @param x coordonnée polaire
     * @param y coordonnée polaire
     */
    public void decalageD(int x, int y){
        crayon = false;
        droite(90);
        avancer(x);
        gauche(90);
        avancer(-y);
        crayon = true;
    }


     /**
     * Décalage du crayon
     * @param x coordonnée polaire
     * @param y coordonnée polaire
     */
     public void decalageG(int x, int y){
        crayon = false;
        droite(90);
        avancer(-x);
        gauche(90);
        avancer(-y);
        crayon = true;
    }

     /**
     * Dessine un immeuble
     */
    public void immeuble(){
        crayon = true;

        for (int i = 0; i< 2; i++){
            avancer(100);
            droite(90);
            avancer(45);
            droite(90);
        }

        droite(90);
        avancer(20);
        gauche(90);
        avancer(10);
        droite(90);
        avancer(5);
        droite(90);
        avancer(10);
        crayon = false;
        droite(90);
        avancer(20);
        droite(90);
        avancer(90);
        crayon = true;

        int coul1 = traitCouleur;
        couleur(5);

        for (int i = 0 ; i < 8; i++) {
            for (int j = 0 ; j < 4 ; j++) {
                window();
                if (j < 4) decalageD(10,0);

            }
            decalageG(40,0);
            decalageD(0,10);
        }
        couleur(coul1);
    }

    /*---------------------------------------------------------->*/



     /**
     * Dessine un astérisque
     * @param t la taille des branches
     * @param n le nombre de branche(s)
     */
    public void asterisque(int t, int n){
        crayon = true;
        for(int i=0; i <= 360;i+=n){
            droite(360/n);
            avancer(t);
            avancer(-t);
        }
    }

}