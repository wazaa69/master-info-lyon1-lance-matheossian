package Vue;

import Model.Terrain;

public class Vue {


    // Les différentes vues
    private VueTerrain vueTerrain;


    public Vue(Terrain unTerrain) {
        this.vueTerrain = new VueTerrain(unTerrain);


    }

    public void initVue(){
        
    }
}
