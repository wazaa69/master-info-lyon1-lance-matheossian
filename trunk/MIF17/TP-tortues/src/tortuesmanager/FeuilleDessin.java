package tortuesmanager;

import java.awt.*;
import javax.swing.*;
import java.util.*;


/**
 * Titre :        Logo
 * Description :  Un exemple de programme graphique utilisant la célèbre Tortue Logo
 * Copyright :    Copyright (c) 2000
 * Société :      LIRMM
 * @author J. Ferber
 * @version 2.0
 */

public class FeuilleDessin extends JPanel {


//######################################################################################################      ATTRIBUTS

    protected static final int rp=10,rb=5; /** pour le tracé de la tortue  */

    boolean showTurtles=true; /** Affichage des tortue Vrai/Faux */

    private ArrayList<Tortue> tortues;   /** liste des tortues */


    Image drawingImage; /** La zone Blanche de dessin  */

    static final double convDegGrad = 0.0174533; // la constante de conversion de degres en gradient


//######################################################################################################      CONSTRUCTEURS

    public FeuilleDessin(){ tortues = new ArrayList<Tortue>(); }
        

//######################################################################################################      ACCESSEURS



    public Tortue getTortue(int i){return tortues.get(i);}
    public ArrayList<Tortue> getListeTortues(){return tortues;}


   public ArrayList<TortueAmelioree> getListeTortuesAmeliorees() {

        ArrayList<TortueAmelioree> listeTortuesAmelioree = new ArrayList<TortueAmelioree>();

        if(!tortues.isEmpty()){
       
            for (int i = 0; i < getListeTortues().size() ; i++)
            {
                    if(getTortue(i) instanceof TortueAmelioree){

                listeTortuesAmelioree.add((TortueAmelioree)getTortue(i));

                }
            }
       }

        return listeTortuesAmelioree;
    }

    /**
    * Retourne le graphique de type Graphics
    */
    public Graphics getImageGraphics(){
        if (drawingImage == null)
            reset();
        return drawingImage.getGraphics();
    }

//######################################################################################################      MUTATEURS

    public void setTortue(ArrayList<Tortue> tortues) {

    this.tortues = tortues;

    }

//######################################################################################################      METHODES

    /**
    * Dessine la tortue
    * @param g le graphique (le support de dessin)
    */
    protected void drawTurtle (Graphics g, Tortue t){

    //Calcule les 3 coins du triangle a partir de
    // la position de la tortue p
    Point p = new Point(t.getX(),t.getY());
    Polygon arrow = new Polygon();

    //Calcule des deux bases
    //Angle de la droite
    double theta=convDegGrad*(-t.dir);
    //Demi angle au sommet du triangle
    double alpha=Math.atan( (float)rb / (float)rp );
    //Rayon de la fleche
    double r=Math.sqrt( rp*rp + rb*rb );
    //Sens de la fleche

    //Pointe
    Point p2=new Point((int) Math.round(p.getX()+r*Math.cos(theta)),
                                         (int) Math.round(p.y-r*Math.sin(theta)));
    arrow.addPoint(p2.x,p2.y);
    arrow.addPoint((int) Math.round( p2.getX()-r*Math.cos(theta + alpha) ),
          (int) Math.round( p2.y+r*Math.sin(theta + alpha) ));

    //Base2
    arrow.addPoint((int) Math.round( p2.x-r*Math.cos(theta - alpha) ),
          (int) Math.round( p2.y+r*Math.sin(theta - alpha) ));

    arrow.addPoint(p2.x,p2.y);
    g.setColor(t.getCouleur()); // on récupère la couleur définie dans tortue
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
    * Efface les dessins et remet la taille de départ
    */
    void reset(){
            Dimension dim=getSize();
            drawingImage = this.createImage(dim.width,dim.height);
            Graphics g = drawingImage.getGraphics();
            Color c = g.getColor();
            g.setColor(Color.white);
            g.fillRect(0,0,dim.width, dim.height);
            g.setColor(c);
    }


    /**
    * Initialise l'image et affiche la/les tortues
    * @param g le graphique
    */
    @Override
    public void paintComponent(Graphics g){
        if (drawingImage==null)
            reset();
        g.drawImage(drawingImage,0,0,null);
        if (showTurtles)
            showTurtles(g);
    }


    /**
    * Génère le fond blanc de la zone de dessin
    */
    public void drawIt(){
        Graphics g = getGraphics();
        g.drawImage(drawingImage,0,0,null);
        if (showTurtles)
            showTurtles(g);
    }


    /**
    * Affiche chaque tortue de la liste général, sur le graphique
    * @param g le graphique
    */
    void showTurtles(Graphics g){
        for(Iterator it = tortues.iterator();it.hasNext();){
            Tortue t = (Tortue) it.next();
            drawTurtle(g,t);
        }
    }




        
}