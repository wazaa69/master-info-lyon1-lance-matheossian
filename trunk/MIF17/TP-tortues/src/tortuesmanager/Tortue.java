package tortuesmanager;

import java.awt.*;


/*************************************************************************

	Un petit Logo minimal qui devra être amélioré par la suite

				J. Ferber - 1999-2001

				Cours de DESS TNI - Montpellier II

	@version 2.0
	@date 25/09/2001

**************************************************************************/

/*
 * La classe Tortue qui se déplace en coordonnées polaires
 */
class Tortue
{

     //######################################################################################################      ATTRIBUTS


    FeuilleDessin feuille;     /** la feuille de dessin sur laquelle dessine la tortue */

    private int x, y;	/** les coordonnees de la tortue */

    double convDegGrad = 0.0174533;     /** la constante de conversion de degres en gradient  */
    protected int dir;	/** la direction de la tortue */
    boolean crayon = true; /** si vrai alors le cayon est baissé, faux sinon */
    protected int traitCouleur; /** couleur du trait de la tortue courante */

    final int distMin; /** distance minimum entre deux tortues */

    /**
    * Couleur de la tortue.
    */
    protected  Color tortueCouleur;


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
        distMin = 15;
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
     * A REVOIR : retourne que des 0 (ce qui ne pose pas de problème pour avancer)
     * Précise si l'emplacement en (x;y) est correcte
     * TODO : il faudrait prendre en compte l'angle d'arrivé de la tortue pour
     * pouvoir le réutiliser, sinon on aura uniquement 8 angles à retourner
     * êter utilisée par la tortue qui tente de se déplacer
     * @param x coordonnée en abscisse
     * @param y coordonnée en ordonné
     * @return retourne 0 si l'emplacement est libre, -1 si impossible, une direction sinon
     */
    public int getBonEmplacement(int newX, int newY){

        
        Tortue uneTortue = null;

        //La position de la tortue ne doit pas être sur une autre
        for (int i = 0; i < feuille.getListeTortues().size(); i++){
            uneTortue = feuille.getTortue(i);
            if(distPoint(newX, newY, uneTortue.getX(), uneTortue.getY()) <= 5) return -1;
        }

        int largeurTerrain = feuille.drawingImage.getWidth(feuille)-5;
        int hauteurTerrain = feuille.drawingImage.getHeight(feuille)-5;

        int distBord = 15;

        /* Teste des condition
        if(newX <= distBord || newX >= largeurTerrain || newY <= distBord || newY >= hauteurTerrain)
        System.out.println(newX + " / " + largeurTerrain +  " - " + newY + " / "+ hauteurTerrain);
        */

        //NE FONCTIONNE PAS ! A REVOIR
        //on tourne dans le sens des aiguilles d'une montre
        //A revoir pour meilleur lisibilité
        if(newX <= distBord && newX < largeurTerrain && newY <= distBord && newY < hauteurTerrain) return (int) 45; //coin supérieur gauche
        if(newX > distBord && newX < largeurTerrain && newY <= distBord && newY < hauteurTerrain) return (int) 90;
        if(newX > distBord && newX >= largeurTerrain && newY <= distBord && newY < hauteurTerrain) return (int) 135;
        if(newX > distBord && newX >= largeurTerrain && newY > distBord && newY < hauteurTerrain) return (int) 180;
        if(newX > distBord && newX >= largeurTerrain && newY > distBord && newY >= hauteurTerrain) return (int) 225;
        if(newX > distBord && newX < largeurTerrain && newY > distBord && newY >= hauteurTerrain) return (int) 270;
        if(newX <= distBord && newX < largeurTerrain && newY > distBord && newY >= hauteurTerrain) return (int) 315;
        if(newX <= distBord && newX < largeurTerrain && newY > distBord && newY < hauteurTerrain) return (int) 360;

        return 0;
    }

    /*--------------------------------------------------------*/
    /*  les procedures de base de fonctionnement de la tortue
    /*--------------------------------------------------------*/
    
   /**
    * Fait avancer la tortue sur une certaine distance
    * @param dist la distance à parcourir en pixel
    */
    public void avancer(int dist)
    {
        int newX = 0, newY  = 0, angle  = 500;
        int nbrEssais= 0;

        Graphics g = feuille.getImageGraphics();

        //Tant que l'emplacement n'est aps bon on cherche de nouvelles coordonnées
        while(nbrEssais <= 20){

            newX = (int) Math.round(x + dist*Math.cos(convDegGrad*dir));
            newY = (int) Math.round(y + dist*Math.sin(convDegGrad*dir));

            //vérifie si l'emplacement est correcte
            angle = getBonEmplacement(newX,newY);

            //if(angle > 0) {dir = angle%360; break;} //quand getBonEmplacement fonctionnera

            if(angle != -1){
                if(angle == 360) {dir = 0; break;}
                else if(angle > 0) {dir = angle; break;}
                else {break;} //sinon c'est que l'angle de base est correcte
            }

            nbrEssais++;
        }


        if(nbrEssais <= 20){

            if (crayon) {
                g.setColor(feuille.decodeColor(traitCouleur));
                g.drawLine(x,y,newX,newY);
            }

            x = newX;
            y = newY;
            
        }
    }


    /**
     * La tortue se déplace aléatoirement
     * @param dist la distance à parcourire
     */
    public void deplacementAuHasard(int dist)
    {
        //Respect de la distance minimale ?
        int distMinimale = distMin;
        if(dist > distMin) distMinimale = dist;

        //déplacement aléatoire
        int angle = (int)(Math.random()*360);
        if(Math.random() > 0.5) droite(angle); else gauche(angle);
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