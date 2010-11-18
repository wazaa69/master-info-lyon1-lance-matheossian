package org.tortue.client.Vue;

import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import org.tortue.client.ClientServeur.ListeClients;

import org.tortue.client.MainEntryPoint;
import org.tortue.client.Modele.Tortue;
import org.tortue.client.Traitement.GWTService;
import org.tortue.client.Traitement.GWTServiceAsync;


/**
 * Vue de la liste des Tortues
 */
public class ListeAutresTortues extends HTMLPanel {

    String id;

    /**
     * Crée un conteneur avec id
     * @param id l'id du conteneur (on se passera de createUniqueId() pour cet APi)
     */
    public ListeAutresTortues(String id) {

        super("<div id='" + id + "'></div>");

        this.id = id; /** on sauvegarde l'identifiant */

        //on ajoute un rafraichissement periodique de la liste des autres tortues et de leur affichage
        Timer t = new Timer() {
          public void run() {
              getListeClients();
              ajoutAutresToruesAVue();
          }
        };

        t.scheduleRepeating(1000); //rafraichissement toutes les secondes

    }


    /**
     * Récupère la liste des clients
     */
    private void getListeClients(){

        GWTServiceAsync svc = (GWTServiceAsync) GWT.create(GWTService.class);

        final AsyncCallback<ListeClients> callback = new AsyncCallback<ListeClients>() {

            public void onSuccess(ListeClients result) {
                MainEntryPoint.LISTECLIENTS.addClients(result);
            }

            public void onFailure(Throwable caught) {
                MainEntryPoint.MESSAGES.setText("Arg !! Récupération de la liste des clients échouée !");
            }
        };

        svc.getListeClients(callback);
    }


    /**
     * Parcours des tortues de chaque client et affichage
     */
    private void ajoutAutresToruesAVue(){

        String tmpStr = "";
        getElementById(id).setInnerHTML(" "); //on efface ce qu'il y a actuellement
        VerticalPanel vPanel = new VerticalPanel();

        VueTerrain.suppAnciennesAutresTortues();

        ListeClients lc = MainEntryPoint.LISTECLIENTS;
        ArrayList<Tortue> listeTortuesClient;

        for(int i = 0; i < lc.size(); i++){

            listeTortuesClient = lc.getClient(i).getListeTortues();

            if(i !=  MainEntryPoint.IDCLIENT){
                for(int j = 0; j < listeTortuesClient.size(); j++){
                    vPanel.add(new Label("Client : " + lc.getClient(i).getNom() + " | " + listeTortuesClient.get(j).getNom()));
                    VueTerrain.addTortueTerrain(MainEntryPoint.VUEAUTRESTORTUES, listeTortuesClient.get(j));
                }
            }
        }

        lc.clear(); //on vide la liste des clients
        add(vPanel, id);

    }

}
