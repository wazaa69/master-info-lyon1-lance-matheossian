package Vue;

import Controleur.JeuDeFoot;
import Model.Terrain;

public class Vue {

    private JeuDeFoot jeuDeFoot;

    // Les différentes vues
    private VueTerrain vueTerrain;

    public Vue(JeuDeFoot jeuDeFoot, Terrain unTerrain) {
        this.jeuDeFoot = jeuDeFoot;
        this.vueTerrain = new VueTerrain(unTerrain);
    }

    public initVue(){
        
    }
}
