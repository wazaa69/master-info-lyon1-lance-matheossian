package Vue;

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

    Terrain unTerrain; /** @param unTerrain le terrain de jeu provenant du model */

    ArrayList<VueJoueur> listeVueJoueurs; /** @param listeVueJoueurs tableau des joueurs pour l'affichage */

    /**
     * Initialisation des valeurs de la vue du terrain
     * @param unTerrain leTerrain provenant du model
     * @param listeJoueurs la liste de tous les joueurs provenant du model
     */
    public VueTerrain(Terrain unTerrain, ArrayList<Joueur> listeJoueurs) {

        this.unTerrain = unTerrain;

        listeVueJoueurs =  new ArrayList<VueJoueur>();

        setBackground(Color.white);
        setPreferredSize(new Dimension(Terrain.LONGUEUR,Terrain.LARGEUR));

        //chaqu joueur du model aura une vue
        for(int i = 0; i < listeJoueurs.size(); i++){
            listeVueJoueurs.add(new VueJoueur(this, listeJoueurs.get(i)));

            //le terrain observe chaque joueur
            listeJoueurs.get(i).ajouterObservateur(new Observateur() {

                public void miseAJour() {
                    
                    if (isOpaque()) resetBackground();
                    dessinerJoueurs(getGraphics());
                    
                }
            });
        }
        
    }


    /**
     * Dessine le fond blanc du terrain
     */
    private void resetBackground(){
        Graphics g = getGraphics();
        Color c = g.getColor();
        g.setColor(Color.white);
        g.fillRect(0,0,Terrain.LONGUEUR,Terrain.LARGEUR); //cadre blanc de fond
        dessinerTerrain();
    }

    /**
     * Dessine les traits du terrain
     */
    private void dessinerTerrain(){

        int longueur = Terrain.LONGUEUR;
        int largeur = Terrain.LARGEUR;

        int longueurCage = 50;
        int largeurCage = 10;

        Graphics g = getGraphics();
        g.setColor(Color.GRAY); //on dessine les traits en noir

        //Cage Gauche
        g.drawRect(-1, (largeur/2) - longueurCage, largeurCage, 2*longueurCage);

        //ligne centrale
        g.drawLine(Math.round(longueur/2), 0, Math.round(longueur/2), largeur );

        //rond centrale
        g.drawOval(Math.round(longueur/2)-30, Math.round(largeur/2)-30, 60, 60);

        //Cage Droite
        g.drawRect(longueur + 1 - largeurCage, (largeur/2) - longueurCage, largeurCage, 2*longueurCage);

    }



    /**
     * Dessine les joueurs
     * @param g contexte graphique
     */
    public void dessinerJoueurs(Graphics g) {

        for(int i = 0; i < listeVueJoueurs.size(); i++)
            listeVueJoueurs.get(i).dessiner(g);

        g.dispose(); //relachement du context graphique

        super.paintComponents(getGraphics());

    }

}