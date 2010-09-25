package tortuesmanager;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/*
 * Copyright : Copyright (c) 2000 - J. Ferber - LIRMM - V 2.0
 */

/**
 * Cette classe représente une feuille de dessin, elle stocke la liste des tortues et les dessine.
 */
public class FeuilleDessin extends JPanel {


//######################################################################################################      ATTRIBUTS

    
    private ArrayList<Tortue> tortues;  /** liste des tortues */

    private Image drawingImage; /** Le support de dessin  */


//######################################################################################################      CONSTRUCTEURS

    /**
     * Constructeur
     */
    public FeuilleDessin(){ tortues = new ArrayList<Tortue>(); }
        

//######################################################################################################      ACCESSEURS

    /**
     * Recherche et retourne une tortue de la liste des tortues
     * @param i la position de la tortue dans la liste de tortues
     * @return retourne une tortue
     */
    public Tortue getTortue(int i){return tortues.get(i);}


    /**
     * Récupère la liste de tortues
     * @return retourne une liste de tortues
     */
    public ArrayList<Tortue> getListeTortues(){return tortues;}



    /**
     * Crée et renvoie une liste de tortues améliorées, à partir de la liste de tortues
     * @return retourne la liste des tortues améliorées
     */
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
     * Crée et renvoie une liste de "tortues equipe", à partir de la liste de tortues
     * @return retourne la liste des "tortues équipe"
     */
    public ArrayList<TortueEquipe> getListeTortuesEquipe() {

        ArrayList<TortueEquipe> listeTortuesEquipe = new ArrayList<TortueEquipe>();

        if(!tortues.isEmpty()){

            for (int i = 0; i < getListeTortues().size() ; i++)
            {
                if(getTortue(i) instanceof TortueEquipe)
                listeTortuesEquipe.add((TortueEquipe)getTortue(i));

            }
       }

        return listeTortuesEquipe;
    }

    /**
     * Retourne le graphique de type Graphics
     * @return retourne le graphique de type Graphics
     */
    public Graphics getImageGraphics(){
        if (drawingImage == null) reset();
        return drawingImage.getGraphics();
    }


    /**
     * Retourne une image
     */

    public Image getDrawingImage() {
        return drawingImage;
    }

//######################################################################################################      METHODES


    /**
    * Recrée la zone de dessin
    */
    protected void reset(){
            Dimension dim=getSize();
            drawingImage = this.createImage(dim.width,dim.height);
            Graphics g = drawingImage.getGraphics();
            Color c = g.getColor();
            g.setColor(Color.white);
            g.fillRect(0,0,dim.width, dim.height);
            g.setColor(c);
    }


    /**
    * Initialise l'image si ce n'est pas déjà fait,
    * Charge l'image,
    * Affiche la/les tortues.
    * @param g le support de dessin
    */
    @Override
    public void paintComponent(Graphics g){
        if (drawingImage==null)
            reset();
        g.drawImage(drawingImage,0,0,null);
        showTurtles(g);
    }


    /**
    * Redessine l'image et les tortues
    */
    public void drawIt(){
        Graphics g = getGraphics();
        g.drawImage(drawingImage,0,0,null);
        showTurtles(g);
    }


    /**
    * Affiche chaque tortue de la liste, sur le graphique
    * @param g le support de dessin
    */
    private void showTurtles(Graphics g){
        for(Tortue uneTortue : getListeTortues()){
            uneTortue.dessinerTortue(g);
        }
    }

       
}