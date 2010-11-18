package org.tortue.server;

import org.tortue.client.ClientServeur.ListeClients;
import org.tortue.client.Modele.Terrain;


/**
 *
 */
public class Factory {

    private ListeClients listeClients = new ListeClients();
    private static Terrain unTerrain = new Terrain(500,500);
    private DernieresTortuesDeplacees listeDTD = new DernieresTortuesDeplacees(0);

    private int nbTortues = 0; /** le nombre de tortue côté serveur*/


    /**
     * L'application démarre quand le premier client arrive
     * @param nom le nom du premier client
     */
    public Factory() {}

    public ListeClients getListeClients() {
        return listeClients;
    }

    public void setListeClients(ListeClients listeClients) {
        this.listeClients = listeClients;
    }

    public Terrain getUnTerrain() {
        return unTerrain;
    }

    public void setUnTerrain(Terrain unTerrain) {
        Factory.unTerrain = unTerrain;
    }

    public DernieresTortuesDeplacees getListeDTD() {
        return listeDTD;
    }

    public int getNbTortues() {
        return nbTortues;
    }

    public void incremanteNbTortues(int nbTortues) {
        this.nbTortues++;
    }

    

    
}
