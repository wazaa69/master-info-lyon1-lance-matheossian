package Vue;

import Model.Ballon;
import Model.Cage;
import Model.JeuDeFoot;
import Model.Joueur;
import Model.Terrain;
import ObservListe.Observateur;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;


/**
 * Cette classe représente la vue du terrain de jeu
 */
public class VueTerrain extends Applet {

    Terrain unTerrain; /** le terrain de jeu provenant du model */

    ArrayList<VueElemMobiles> listeVueElemMobiles; /** tableau des joueurs pour l'affichage */

    /**
     * Initialisation des valeurs de la vue du terrain
     * @param unTerrain leTerrain provenant du model
     * @param listeVueElemMobiles la liste de tous les joueurs provenant du model
     */
    public VueTerrain(Terrain unTerrain, ArrayList<Joueur> listeJoueurs, Ballon unBallon) {

        this.unTerrain = unTerrain;

        this.listeVueElemMobiles =  new ArrayList<VueElemMobiles>();

        setBackground(Color.white);
        setPreferredSize(new Dimension(Terrain.LONGUEUR,Terrain.LARGEUR));

        //Création des vues pour chaque joueur
        for(int i = 0; i < listeJoueurs.size(); i++){
            
            this.listeVueElemMobiles.add(new VueJoueur(listeJoueurs.get(i)));

            //le terrain observe chaque joueur
            listeJoueurs.get(i).ajouterObservateur(new Observateur() {

                public void miseAJour() {
                    resetBackground();
                    dessinerElemMobiles(getGraphics()); 
                }
            });
        }

        //Vue du ballon
        this.listeVueElemMobiles.add(new VueBallon(unBallon));
        
    }


    /**
     * Dessine le fond blanc du terrain
     */
    private void resetBackground(){
        Graphics g = getGraphics();
        Color c = g.getColor();
        g.setColor(Color.white);
        //cadre blanc de fond --> fait scintiller l'affichage : redessine le tout
        g.fillRect(0,0,Terrain.LONGUEUR,Terrain.LARGEUR);
        dessinerTerrain();
    }

    /**
     * Dessine les traits du terrain
     */
    private void dessinerTerrain(){

        int longueur = Terrain.LONGUEUR;
        int largeur = Terrain.LARGEUR;

        Graphics g = getGraphics();
        g.setColor(Color.GRAY); //on dessine les traits en noir

        Cage uneCage;

        //Cage Gauche
        uneCage = unTerrain.getCageGauche();
        g.drawRect((int) uneCage.getCoordonnees().getX(), (int)uneCage.getCoordonnees().getY(),
                uneCage.getLargeur(), uneCage.getLongueur());

        //ligne centrale
        g.drawLine(Math.round(longueur/2), 0, Math.round(longueur/2), largeur );

        //rond centrale
        g.drawOval(Math.round(longueur/2)-30, Math.round(largeur/2)-30, 60, 60);

        //Cage Droite
        uneCage = unTerrain.getCageDroite();
        g.drawRect((int) uneCage.getCoordonnees().getX(), (int)uneCage.getCoordonnees().getY(),
                uneCage.getLargeur(), uneCage.getLongueur());

    }



    /**
     * Dessine les joueurs
     * @param g contexte graphique
     */
    public void dessinerElemMobiles(Graphics g) {

        for(int i = 0; i < listeVueElemMobiles.size(); i++)
            listeVueElemMobiles.get(i).dessiner(g, false);

        super.paintComponents(getGraphics());

    }

}