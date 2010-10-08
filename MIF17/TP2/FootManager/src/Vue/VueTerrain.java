package Vue;

import Model.Terrain;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class VueTerrain extends JPanel {

    Terrain unTerrain;

    Image drawingImage;

    public VueTerrain(Terrain unTerrain) {
        this.unTerrain = unTerrain;
        setBackground(Color.white);
        setPreferredSize(new Dimension(unTerrain.getLongeur(),unTerrain.getLargeur()));
    }

    /**
     * Crée le terrain
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


    /**
     * Redéfinition de la méthode paintComponent() de JPanel (héritée de JComponent)
     * @param g
     */
    @Override
    public void paintComponent(Graphics g){
        if (drawingImage == null) reset();
        g.drawImage(drawingImage,0,0,null);
        montrerJoueurs(g);
    }


    public void drawIt(){
        Graphics g = getGraphics();
        g.drawImage(drawingImage,0,0,null);
    }
    

    public Graphics getImageGraphics(){
        if (drawingImage == null) reset();
        return drawingImage.getGraphics();
    }

    private void montrerJoueurs(Graphics g){
        //for(Iterator it = tortues.iterator();it.hasNext();){
          //  Tortue t = (Tortue) it.next();
        //}
    }


}