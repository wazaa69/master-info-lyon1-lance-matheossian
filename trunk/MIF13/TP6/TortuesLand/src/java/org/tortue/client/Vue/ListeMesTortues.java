package org.tortue.client.Vue;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.tortue.client.Modele.Tortue;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.tortue.client.MainEntryPoint;
import org.tortue.client.Traitement.GWTService;
import org.tortue.client.Traitement.GWTServiceAsync;

/**
 * Vue de la liste des Tortues
 */
public class ListeMesTortues extends HTMLPanel {


    private MenuBar menuDesTortues; /** l'identifiant du conteneur */
    private int numTortueAjoute; /** le numéro de la dernière tortue ajoutée : utile pour les commandes */


    private Tortue tmpTortue; /** une tortue temporaire */
    private boolean addTortue = false; /** le booléen pour savoir si on ajoute ou non une tortue */


    private ArrayList<Tortue> mesTortues = new ArrayList<Tortue>(); /** la liste des tortues du joueurs */

    
    /**
     * Crée un conteneur avec id
     * @param id l'id du conteneur (on se passera de createUniqueId() pour cet APi)
     */
    public ListeMesTortues(String id) {

        super("<div id='" + id + "'></div>");
        setStyleName("conteneur" + id);

        //balise formulaire
        FormPanel formulaire = new FormPanel();
        formulaire.setEncoding(FormPanel.ENCODING_MULTIPART);
        formulaire.setMethod(FormPanel.METHOD_POST);

        VerticalPanel vPanel = new VerticalPanel();
        Button ajouterTortue = new Button("[+1 Tortue]");
        vPanel.add(ajouterTortue);

        /*  1 - on crée une tortue
         *  2 - on prévient le serveur de cette création
         *  3 - selon sa réponse, on ajoute ou non la tortue dans la liste du client
         */
        ajouterTortue.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {

                numTortueAjoute = mesTortues.size()+1;

                tmpTortue = new Tortue("Tortue-"+numTortueAjoute,numTortueAjoute, 0, 0);

                //si la tortue est ajouté côté server, on l'ajoute côté client
                if(addTortue(tmpTortue)){

                    mesTortues.add(tmpTortue); //Ajout au Modèle
                    Outils.tortueCourante = tmpTortue; //Mise à jour de la tortue courante

                    //Création d'une commande pour la tortue qui va être affichée dans le menu
                    Command majTortueCourante = new ItemTortueCommand(tmpTortue);

                    //Ajout d'un Item au menu de la Vue
                    MenuItem itemTortue = new MenuItem(tmpTortue.getNom(), majTortueCourante);
                    menuDesTortues.addItem(itemTortue);

                    addTortue = false; //remise à zéro du booléen
                }

            }
        });

        formulaire.add(vPanel);
        
        //ajout du formulaire
        add(formulaire, id);
        
        //création et ajout du menu
        menuDesTortues = new MenuBar();
        add(menuDesTortues, id);

    }

    /**
     * Ajoute une tortue côté serveur
     * Si une erreur est retournée, la tortue n'est pas ajouté au Client/Serveur
     */
    public boolean addTortue(final Tortue tmpTortue){

        GWTServiceAsync svc = (GWTServiceAsync) GWT.create(GWTService.class);

        final AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

            public void onSuccess(Boolean result) {
                addTortue = result.booleanValue();
		MainEntryPoint.MESSAGES.setText(tmpTortue.getNom() + " ajoutée côté Client et Serveur.");
            }

            public void onFailure(Throwable caught) {
                MainEntryPoint.MESSAGES.setText("Serveur erreur : Ajout de Tortue Echoué (l'ajout côté client a aussi été annulé).");
            }
        };

        svc.addTortue(MainEntryPoint.IDCLIENT, tmpTortue.getNom(), callback);

        return addTortue;

    }

}
