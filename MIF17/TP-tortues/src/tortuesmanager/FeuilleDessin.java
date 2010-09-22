package tortuesmanager;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/*
 * Copyright :    Copyright (c) 2000 - J. Ferber - LIRMM - V 2.0
 */

/**
 * Cette classe représente une feuille de dessin, elle stocke la liste des tortues et les dessine.
 */
public class FeuilleDessin extends JPanel {


//######################################################################################################      ATTRIBUTS


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
                if(getTortue(i) instanceof TortueAmelioree)
                listeTortuesAmelioree.add((TortueAmelioree)getTortue(i));

            }
       }

        return listeTortuesAmelioree;
    }

    /**
    * Retourne le graphique de type Graphics
    */
    public Graphics getImageGraphics(){
        if (drawingImage == null) reset();
        return drawingImage.getGraphics();
    }

//######################################################################################################      MUTATEURS

    public void setTortue(ArrayList<Tortue> tortues) {this.tortues = tortues;}

//######################################################################################################      METHODES


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
        for(Tortue uneTortue : getListeTortues()){
            uneTortue.dessinerTortue(g);
        }
    }

       
}