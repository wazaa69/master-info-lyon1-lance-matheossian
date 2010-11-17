package org.tortue.client;

import org.tortue.client.Vue.Outils;
import org.tortue.client.Vue.VueTerrain;
import org.tortue.client.Vue.ListeMesTortues;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import org.tortue.client.Modele.Terrain;
import org.tortue.client.Modele.Tortue;
import org.tortue.client.Traitement.GWTService;
import org.tortue.client.Traitement.GWTServiceAsync;
import org.tortue.client.Vue.ListeAutresTortues;

/**
 * Page affichée
 */
public class MainEntryPoint implements EntryPoint {


    Label messages = new Label("..........");

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
        getDataServ();
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

        RootPanel.get().add(messages);
        RootPanel.get().add(vPanelOutilsTerrain);
        RootPanel.get().add(vPanelListe);
    }


    /**
     * Assigne un id au client.
     * (On pourrait même récupérer une classe contenant divers informations)
     */
    public void getDataServ(){

        //définition du service que l'on souhaite appeler
        GWTServiceAsync svc = (GWTServiceAsync) GWT.create(GWTService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) svc;

        //On définit ou trouver le service (son url relative au serveur et au dossier de base)
        String moduleRelativeURL = GWT.getModuleBaseURL() + "traitement/gwtservice";
        endpoint.setServiceEntryPoint(moduleRelativeURL);

        /*
         * C'est au travers de l'objet AsyncCallback que le serveur retournera sa réponse au client.
         * On définit par la même occasion le comportement de retour.
         */
        final AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

            //en cas de succes
            public void onSuccess(Integer result) {
                //On récupère la valeur retournée par le serveur (un String).
                //Et on la met à jour chez le Client.
                idClient = result.intValue();
                messages.setText("n°Client serveur : " + result);
            }

            //en cas d'echec
            public void onFailure(Throwable caught) {
                messages.setText("Arg !! Connexion Echouée, si c'est votre première connexion alors rechargez la page");
            }
        };

        /*
         * on appelle le service pour récupérer le champs envoyé au serveur
         * on lui envoie le contenu du champs (c'est un pue bête mais c'est pour tester)
         * (par exemple, le message pourrait être stocké sur le server)
         */
        svc.getId("bob", callback);

    }

}
