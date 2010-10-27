/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Vue;

import Vue.Controls.FenetreControls;
import Model.JeuDeFoot;
import Vue.Terrain.FenetreTerrain;

/**
 * Cette classe va creer les diférentes fenêtres du jeu
 */
public class Vue {

    private JeuDeFoot unJeuDeFoot; /** référence sur le jeu de foot*/

    private FenetreTerrain fenetreTerrain;  /** la vue du terrain */
    private FenetreCouleurEtScore fenetreCouleurEtScore; /** la vue du score et de la couleur de l'équipe en possession de la balle */
    private FenetreControls fenetreControls; /** la vue qui perme de choisir une stratégie par équipe */


/*******************************  CONSTRUCTEUR  *******************************/

    
    public Vue(JeuDeFoot unJeuDeFoot) {

        unJeuDeFoot = unJeuDeFoot;

        fenetreTerrain = new FenetreTerrain(unJeuDeFoot);

        fenetreCouleurEtScore = new FenetreCouleurEtScore(unJeuDeFoot);
        
        fenetreControls = new FenetreControls(unJeuDeFoot);

    }

/******************************  GETTER/SETTERS  ******************************/

    public FenetreControls getFenetreControls() {
        return fenetreControls;
    }

    public FenetreCouleurEtScore getFenetreCouleurEtScore() {
        return fenetreCouleurEtScore;
    }

    public FenetreTerrain getFenetreTerrain() {
        return fenetreTerrain;
    }

    public JeuDeFoot getUnJeuDeFoot() {
        return unJeuDeFoot;
    }


}
