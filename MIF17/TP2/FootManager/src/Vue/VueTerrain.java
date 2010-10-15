package Vue;

import Model.Joueur;
import Model.Terrain;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Cette classe affiche le terrain et son contenu
 */
public class VueTerrain extends JPanel {

    Terrain unTerrain;

    ArrayList<VueJoueur> listeVueJoueurs;

    Image drawingImage;


    /**
     * Initialisation des valeurs de la vue du terrain
     * @param unTerrain leTerrain provenant du model
     * @param listeJoueurs la liste de tous les joueurs du model
     */
    public VueTerrain(Terrain unTerrain, ArrayList<Joueur> listeJoueurs) {

        this.unTerrain = unTerrain;

        listeVueJoueurs =  new ArrayList<VueJoueur>();

        //chaqu joueur du model aura une vue
        for(int i = 0; i < listeJoueurs.size(); i++)
            listeVueJoueurs.add(new VueJoueur(this, listeJoueurs.get(i)));
        
        setBackground(Color.white);
        setPreferredSize(new Dimension(unTerrain.getLongeur(),unTerrain.getLargeur()));

    }


    /**
     * Redéfinition de la méthode paintComponent() de JPanel (héritée de JComponent)
     * @param g
     */
    @Override
    public void paintComponent(Graphics g){
        if (drawingImage == null) reset(); //initialise juste
        g.drawImage(drawingImage,0,0,null); //--> scnintillement venai peut etre de la
    }


    public Graphics getImageGraphics(){
        if (drawingImage == null) reset();
        return drawingImage.getGraphics();
    }


    /**
     * Créaton de la vue du terrain
     */
    private void reset(){
        drawingImage = this.createImage(unTerrain.getLongeur(),unTerrain.getLargeur());
        Graphics g = drawingImage.getGraphics();
        Color c = g.getColor();
        g.setColor(Color.white);
        //g.fillRect(0,0,dim.width, dim.height);
        g.fillRect(0,0,500, 500);
        g.setColor(c);
    }
    

}