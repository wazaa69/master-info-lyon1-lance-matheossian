package Vue.Terrain;

import Model.Terrain.Cage;
import Model.ElementMobile.Joueur;
import Model.JeuDeFoot;
import Model.Terrain.Terrain;
import ObservListe.Observeur;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


/**
 * Cette classe représente la vue du terrain de jeu
 * @see <a href="http://www.realapplets.com/tutorial/DoubleBuffering.html">Double buffering</a>
 */
public class VueTerrain extends Applet {

    Terrain unTerrain; /** le terrain de jeu provenant du model */

    ArrayList<VueElemMobiles> listeVueElemMobiles; /** tableau des joueurs pour l'affichage */

    //Buffering
    Graphics bufferGraphics;
    Image offscreen;


/*******************************  CONSTRUCTEUR  *******************************/


    /**
     * Initialisation de la vue du terrain, à partir du terrain du model
     * Création des vues des élémens mobiles, pour chaque élément mobile du model
     * @param unTerrain le terrain provenant du model
     */
    public VueTerrain(JeuDeFoot unJeuDeFoot) {

        setBackground(Color.white);
        setPreferredSize(new Dimension(Terrain.LONGUEUR,Terrain.LARGEUR));


        this.unTerrain = unJeuDeFoot.getUnTerrain();

        ArrayList<Joueur> listeJoueurs = unJeuDeFoot.getJoueurs();
        this.listeVueElemMobiles =  new ArrayList<VueElemMobiles>();


        //Création des vues pour chaque joueur
        for(int i = 0; i < listeJoueurs.size(); i++){
            
            this.listeVueElemMobiles.add(new VueJoueur(listeJoueurs.get(i)));

            //le terrain observe chaque joueur
            listeJoueurs.get(i).ajouterObserveur(new Observeur() {

                public void miseAJour() {
                    repaint();
                }

            });
        }

        //Vue du ballon
        this.listeVueElemMobiles.add(new VueBallon(unJeuDeFoot.getUnBallon()));
        
    }


/*********************************  OVERRIDE  *********************************/


    /**
     * Initialisation pour le double buffering
     */
    @Override
    public void init(){

        if(offscreen == null)
            offscreen = createImage(Terrain.LONGUEUR, Terrain.LARGEUR);
        
        if(bufferGraphics == null)
            bufferGraphics = offscreen.getGraphics();

    }

    /**
     * Redessinne le terrain et les éléments mobiles, puis les affiches
     * @param g le contexte graphique
     */
    @Override
    public void paint(Graphics g){
        
        //pour éviter que tout se dessine sans que la partie ai commencée
        if(JeuDeFoot.isPartieEnCours()){
            init();
            toutEffacer();
            dessinerTerrain();
            dessinerElemMobiles();
            g.drawImage(offscreen,0,0,this);
        }

    }

    /**
     * Mise à jour du dessin
     */
    @Override
    public void update(Graphics g) {
        paint(g);
    }


/********************************* DESSIN **************************************/

    /**
     * Efface tout ce qu'il y a sur le terrain
     */
    public void toutEffacer(){
        Color c = bufferGraphics.getColor();
        bufferGraphics.setColor(Color.WHITE);
        //cadre blanc de fond --> fait scintiller l'affichage : redessine le tout
        bufferGraphics.fillRect(0,0,Terrain.LONGUEUR,Terrain.LARGEUR);
        dessinerTerrain();
    }

    /**
     * Dessine les traits du terrain
     */
    private void dessinerTerrain(){

        int longueur = Terrain.LONGUEUR;
        int largeur = Terrain.LARGEUR;

        bufferGraphics.setColor(Color.BLACK); //on dessine les traits en gris

        Cage uneCage = null;

        //Cage Gauche
        uneCage = unTerrain.getCageGauche();
        bufferGraphics.drawRect((int) uneCage.getCoordonnees().getX()-1, (int)uneCage.getCoordonnees().getY(),
                uneCage.getLargeur(), uneCage.getLongueur());

        //ligne centrale
        bufferGraphics.drawLine(Math.round(longueur/2), 0, Math.round(longueur/2), largeur );

        //rond centrale
        bufferGraphics.drawOval(Math.round(longueur/2)-30, Math.round(largeur/2)-30, 60, 60);

        //Cage Droite
        uneCage = unTerrain.getCageDroite();
        bufferGraphics.drawRect((int) uneCage.getCoordonnees().getX(), (int)uneCage.getCoordonnees().getY(),
                uneCage.getLargeur(), uneCage.getLongueur());

    }


    /**
     * Dessine les joueurs
     * @param g contexte graphique
     */
    public void dessinerElemMobiles() {

        for(int i = 0; i < listeVueElemMobiles.size(); i++)
            listeVueElemMobiles.get(i).dessiner(bufferGraphics);

    }


}