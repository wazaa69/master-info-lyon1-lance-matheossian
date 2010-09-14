package tortuesmanager;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


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

    double convDegGrad = 0.0174533;     /** la constante de conversion de degres en gradient  */

    FeuilleDessin feuille;     /** la feuille de dessin sur laquelle dessine la tortue */


    int x, y;	/** les coordonnees de la tortue */
    int dir;	/** la direction de la tortue */
    boolean crayon=true; /** par defaut on suppose qu'on dessine */
    int coul; /** couleur courante */


   /**
    * Constructeur
    * @param f la feuille de dessin
    */
    public Tortue(FeuilleDessin f){
        reset();
        feuille = f;
        f.tortues.add(this);
    }

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



    /*--------------------------------------------------------*/
    /*  les procedures de base de fonctionnement de la tortue
    /*--------------------------------------------------------*/
    
    /**
    * Fait avancer la tortue sur une distance
    * @param dist la distance à parcourir
    */
    void avancer(int dist)
    {
        Graphics g = feuille.getImageGraphics();

        int newX = (int) Math.round(x+dist*Math.cos(convDegGrad*dir));
        int newY = (int) Math.round(y+dist*Math.sin(convDegGrad*dir));
        if (crayon) {
                g.setColor(feuille.decodeColor(coul));
                g.drawLine(x,y,newX,newY);
        }
        x = newX;
        y = newY;
        //if (getShowTurtle())
        //  this.drawTurtle(g);
        //System.out.println("dir: " + dir);
        System.out.println("Tortue::avancer");
        feuille.drawIt();
    }


    /**
    * Aller à droite
    * @param ang l'angle de rotation
    */
    void droite(int ang)
    {
            dir = (dir + ang) % 360;
            feuille.drawIt();
    }

    /**
    * Aller à gauche
    * @param ang l'angle de rotation
    */
    void gauche(int ang)
    {
            dir = (dir - ang) % 360;
            feuille.drawIt();
    }

    /**
    *  Baisse le crayon pour dessiner
    */
    void baisserCrayon(){crayon = true;}

    /**
     * lever le crayon pour ne plus dessiner
     */
    void leverCrayon(){crayon = false;}

    /**
     * Attribut une nouvelle couleur au trait de dessin
     * @param n le code couleur entier positif
     */
    void couleur(int n){coul = n % 12;}

    /**
     * Passer à la couleur suivante
     */
    void couleurSuivante() {couleur(coul+1);}


    /**
     * Déplacement aléatoire, la position initial équivaut à un cercle
     */
    public void deplacementsHasardeux()
    {
        
        int l = feuille.drawingImage.getWidth(feuille);
        int h = feuille.drawingImage.getHeight(feuille);

        int angle=0, newX =0, newY=0, nbrEssaies = 0;

        while(nbrEssaies < 5){
            angle = (int)(Math.random()*360);
            droite(angle);

            //10 de déplacement
            newX = (int) Math.round(x+10*Math.cos(convDegGrad*dir));
            newY = (int) Math.round(y+10*Math.sin(convDegGrad*dir));

           if(newX > 0 && newY > 0 && newX < l && newY < h) break;

            nbrEssaies++;
        }

        if(nbrEssaies < 5){
            droite(angle);
            avancer(10);
        }
    }



    /*-----------------------------------*/
    /*              FIGURES
    /*-----------------------------------*/

    /**
     * Dessine un carré
     */
    void carre() {
        for (int i=0;i<4;i++) {
                avancer(100);
                droite(90);
        }
    }


    /**
     * Dessine un polygone
     */
    void poly(int n, int a) {
        for (int j=0;j<a;j++) {
                avancer(n);
                droite(360/a);
        }
    }

     /**
     * Dessine une spirale
     */
    void spiral(int n, int k, int a) {
        for (int i = 0; i < k; i++) {
                couleur(coul+1);
                avancer(n);
                droite(360/a);
                n = n+1;
        }
    }


    /*-----------------------IMMEUBLE----------------------------->*/

     /**
     * Dessine une fenêtre
     */
    void window(){
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
    void decalageD(int x, int y){
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
     void decalageG(int x, int y){
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
    void immeuble(){
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

        int coul1 = coul;
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
    void asterisque(int t, int n){
        crayon = true;
        for(int i=0; i <= 360;i+=n){
            droite(360/n);
            avancer(t);
            avancer(-t);
        }
    }

    /*-----------------------------------*/
    /*         GETTERS/SETTERS
    /*-----------------------------------*/


    public void setColor(int n){coul = n;}
    public int getColor() {return coul;}


}
