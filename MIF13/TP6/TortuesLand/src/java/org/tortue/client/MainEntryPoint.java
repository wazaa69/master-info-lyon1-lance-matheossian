package org.tortue.client;

import java.util.ArrayList;
import org.tortue.client.Vue.Outils;
import org.tortue.client.Vue.VueTerrain;
import org.tortue.client.Vue.ListeMesTortues;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.tortue.client.ClientServeur.ListeClients;
import org.tortue.client.Modele.Terrain;
import org.tortue.client.Modele.Tortue;
import org.tortue.client.Traitement.GWTService;
import org.tortue.client.Traitement.GWTServiceAsync;
import org.tortue.client.Vue.ListeAutresTortues;


/**
 * Page affichée
 */
public class MainEntryPoint implements EntryPoint {

    public static Label INFOMESS = new Label(".........."); /** le message affiché selon les actions*/
    private int idClient; /** l'id distribuée par le server */

    private ArrayList<Tortue> mesTortues = new ArrayList<Tortue>(); /** la liste des tortues du joueurs */
    private ListeClients listeClients = new ListeClients(); /** la liste des clients => liste des auters tortues */

    private Terrain unTerrain = new Terrain(500,500);

    private VueTerrain vueTerrain; /** la vue du terrain */
    private ArrayList<HTMLPanel> vueMesTortues = new ArrayList<HTMLPanel>(); /** vue de chaque tortue du client */
    private ArrayList<HTMLPanel> vueAutresTortues = new ArrayList<HTMLPanel>(); /** vue de chaque tortue des autres clients */

    //private Modele leModele = new Model();

    /** 
     * Création d'une nouvelle instance et initialisation des classes
     * Pas de vérification pour savoir si le joueur existe déjà sur le server (donc pas d'antécédant)
     */
    public MainEntryPoint() {
        getMyId();
    }

    /**
     * Chargement de la page
     * @see voir version "WebApplication" précédante pour les commentaires
     */
    public void onModuleLoad() {
        VerticalPanel vPanelOutilsTerrain = new VerticalPanel();
        vPanelOutilsTerrain.getElement().setId("OutilsTerrain");
        VerticalPanel vPanelListe = new VerticalPanel();
        vPanelListe.getElement().setId("Liste");

        vPanelOutilsTerrain.add(new Outils("Outils", this));

        vueTerrain = new VueTerrain("Terrain", this, unTerrain.getLongueur(), unTerrain.getLargeur());
        vPanelOutilsTerrain.add(vueTerrain);
        vPanelListe.add(new ListeMesTortues("MesTortues", this));
        vPanelListe.add(new ListeAutresTortues("AutresTortues", this));

        INFOMESS.getElement().setId("Message");
        RootPanel.get().add(INFOMESS);
        RootPanel.get().add(vPanelOutilsTerrain);
        RootPanel.get().add(vPanelListe);

    }


    /**
     * Assigne un id au client.
     * (On pourrait même récupérer une classe contenant divers informations)
     */
    public final void getMyId(){

        //définition du service que l'on souhaite appeler (pas besoin de définir la route)
        GWTServiceAsync svc = (GWTServiceAsync) GWT.create(GWTService.class);

        final AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

            //en cas de succes
            public void onSuccess(Integer result) {
                //On récupère la valeur retournée par le serveur (un String).
                //Et on la met à jour chez le Client.
                idClient = result.intValue();
                INFOMESS.setText("n°Client serveur : " + result + ".");
            }

            //en cas d'echec
            public void onFailure(Throwable caught) {
                INFOMESS.setText("Arg !! Connexion Echouée, si c'est votre première connexion alors rechargez la page (F5)");
            }
        };

        /*
         * on appelle le service pour récupérer le champs envoyé au serveur
         * on lui envoie le contenu du champs (c'est un pue bête mais c'est pour tester)
         * (par exemple, le message pourrait être stocké sur le server)
         */
        svc.getId("bob", callback);

    }

    public int getIdClient() {
        return idClient;
    }

    public ListeClients getListeClients() {
        return listeClients;
    }

    public ArrayList<Tortue> getMesTortues() {
        return mesTortues;
    }

    public Terrain getUnTerrain() {
        return unTerrain;
    }

    public ArrayList<HTMLPanel> getVueAutresTortues() {
        return vueAutresTortues;
    }

    public ArrayList<HTMLPanel> getVueMesTortues() {
        return vueMesTortues;
    }

    public VueTerrain getVueTerrain() {
        return vueTerrain;
    }

}
