package org.tortue.client;

import org.tortue.client.Vue.Outils;
import org.tortue.client.Vue.VueTerrain;
import org.tortue.client.Vue.ListeMesTortues;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import org.tortue.client.Modele.Terrain;
import org.tortue.client.Modele.Tortue;
import org.tortue.client.Vue.ListeAutresTortues;

/**
 * Page affichée
 */
public class MainEntryPoint implements EntryPoint {


    private int idClient; /** l'id distribuée par le server */

    private Terrain unTerrain = new Terrain(500,500);
    
    private ArrayList<Tortue> mesTortues = new ArrayList<Tortue>(); /** la liste des tortues du joueurs */
    private ArrayList<Tortue> autresTortues = new ArrayList<Tortue>(); /** la liste des autres tortues des Clients */


    /** 
     * Création d'une nouvelle instance et initialisation des classes
     * Pas de vérification pour savoir si le joueur existe déjà sur le server (donc pas d'antécédant)
     */
    public MainEntryPoint() {
        //on pourrait interroger le serveur pour connaître la taille du terrain
    }

    /**
     * Chargement de la page
     * @see voir version "WebApplication" précédante pour les commentaires
     */
    public void onModuleLoad() {


        VerticalPanel vPanelOutilsTerrain = new VerticalPanel();
        VerticalPanel vPanelListe = new VerticalPanel();

        
        int longueurT = unTerrain.getLongueur() - unTerrain.getMargint();
        int largeurT = unTerrain.getLargeur() - unTerrain.getMargint();
        
        
        vPanelOutilsTerrain.add(new Outils("outils"));
        vPanelOutilsTerrain.add(new VueTerrain("terrain", longueurT, largeurT));
        vPanelListe.add(new ListeMesTortues("MesTortues"));
        vPanelListe.add(new ListeAutresTortues("AutresTortues"));

        RootPanel.get().add(vPanelOutilsTerrain);
        RootPanel.get().add(vPanelListe);
    }

}
