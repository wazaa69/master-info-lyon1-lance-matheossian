package org.tortue.client;

import org.tortue.client.Vue.Outils;
import org.tortue.client.Vue.VueTerrain;
import org.tortue.client.Vue.ListeTortues;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import org.tortue.client.Modele.Terrain;
import org.tortue.client.Modele.Tortue;

/**
 * Page affichée
 */
public class MainEntryPoint implements EntryPoint {


    private int idClient; /** l'id distribuée par le server */
    
    private ArrayList<Tortue> mesTortues = new ArrayList<Tortue>(); /** la liste des tortues du joueurs */
    private ArrayList<Tortue> autresTortues = new ArrayList<Tortue>(); /** la liste des autres tortues des Clients */


    /** 
     * Création d'une nouvelle instance et initialisation des classes
     * Pas de vérification pour savoir si le joueur existe déjà sur le server (donc pas d'antécédant)
     */
    public MainEntryPoint() {
        
    }

    /**
     * Chargement de la page
     * @see voir version "WebApplication" précédante pour les commentaires
     */
    public void onModuleLoad() {
      
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setStyleName("MainConteneur");

        vPanel.add(new Outils());
        vPanel.add(new VueTerrain(Terrain.LONGUEUR - Terrain.MARGEINT, Terrain.LARGEUR - Terrain.MARGEINT));
        vPanel.add(new ListeTortues("mesTortues"));
        vPanel.add(new ListeTortues("autresTortues"));
        
        RootPanel.get().add(vPanel);
        
    }

}
