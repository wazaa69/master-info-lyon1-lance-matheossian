/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Vue;

import Vue.Controles.FenetreControles;
import Model.JeuDeFoot;
import Vue.Terrain.FenetreTerrain;

/**
 * Cette classe va creer les diférentes fenêtres (vues) du jeu
 */
public class Vue {

    private JeuDeFoot unJeuDeFoot; /** référence sur le jeu de foot*/

    private FenetreTerrain fenetreTerrain;  /** la vue du terrain */
    private FenetreCouleurEtScore fenetreCouleurEtScore; /** la vue du score et de la couleur de l'équipe en possession de la balle */
    private FenetreControles fenetreControls; /** la vue qui perme de choisir une stratégie par équipe */


/*******************************  CONSTRUCTEUR  *******************************/


    /**
     * Crée et initialise les différentes fenêtres du jeu
     * @param unJeuDeFoot un jeu de foot
     */
    public Vue(JeuDeFoot unJeuDeFoot) {

        this.unJeuDeFoot = unJeuDeFoot;

        fenetreTerrain = new FenetreTerrain(unJeuDeFoot);

        fenetreCouleurEtScore = new FenetreCouleurEtScore(unJeuDeFoot);
        
        fenetreControls = new FenetreControles(unJeuDeFoot);

    }

/******************************  GETTER/SETTERS  ******************************/

    public FenetreControles getFenetreControls() {
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
