package Vue.Terrain;

import Model.JeuDeFoot;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * Le conteneur du terrain
 */
public class FenetreTerrain extends JFrame {


    /**
     * Crée la fenêtre et y ajoute le terrain
     * @param unJeuDeFoot le jeu de foot
     */
    public FenetreTerrain(JeuDeFoot unJeuDeFoot){

        getContentPane().setLayout(new BorderLayout(10,10));

        //taille de la fenêtre
        setSize(800,600);
        this.setResizable(false);

        // Centre l'interface
        setLocationRelativeTo(null);
        setTitle("Terrain");

        //ferme la (les) fenêtres
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Ajout du terrain dans la fenêtre
        getContentPane().add(new VueTerrain(unJeuDeFoot), BorderLayout.CENTER);

        pack();
        setVisible(true);
        
    }



}
