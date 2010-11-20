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

    MainEntryPoint mep;
    String id;

    /**
     * Crée un conteneur avec id
     * @param id l'id du conteneur (on se passera de createUniqueId() pour cet APi)
     */
    public ListeAutresTortues(String id, MainEntryPoint mep) {

        super("<div class='titreListe'>Autres tortues :</div><div id='" + id + "'></div>");
        getElement().setId("conteneur"+id);

        getElementById(id).setAttribute("style", "overflow-y:scroll;"); //pas accepté dans la css

        this.mep = mep;
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
                mep.getModele().getListeClients().addClients(result);
            }

            public void onFailure(Throwable caught) {
                MainEntryPoint.INFOMESS.setText("Arg !! Récupération de la liste des clients échouée !");
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

        mep.getVueTerrain().suppAnciennesAutresTortues();

        ListeClients lc = mep.getModele().getListeClients();
        ArrayList<Tortue> listeTortuesClient;

        for(int i = 0; i < lc.size(); i++){

            listeTortuesClient = lc.getClient(i).getListeTortues();

            if(i !=  mep.getIdClient()){
                for(int j = 0; j < listeTortuesClient.size(); j++){
                    vPanel.add(new Label("Client : " + lc.getClient(i).getNom() + " | " + listeTortuesClient.get(j).getNom()));
                    mep.getVueTerrain().addTortueTerrain(mep.getVueAutresTortues(), listeTortuesClient.get(j));
                }
            }
        }

        lc.clear(); //on vide la liste des clients
        add(vPanel, id);

    }

}
