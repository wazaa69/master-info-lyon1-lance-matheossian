package org.tortue.client.Modele;

import java.util.ArrayList;
import org.tortue.client.ClientServeur.ListeClients;

/**
 * Le modèle principale
 */
public class Modele {

    private Terrain unTerrain = new Terrain(500,500);
    private ArrayList<Tortue> mesTortues = new ArrayList<Tortue>(); /** la liste des tortues du joueurs */
    private ListeClients listeClients = new ListeClients(); /** la liste des clients => liste des auters tortues */

    public Modele() {}

    public Terrain getUnTerrain() {
        return unTerrain;
    }

    public ListeClients getListeClients() {
        return listeClients;
    }

    public ArrayList<Tortue> getMesTortues() {
        return mesTortues;
    }

}
