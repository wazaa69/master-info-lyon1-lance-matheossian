package org.tortue.client.Vue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
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


        ajouterTortue.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                    addTortueServeurAndClient();
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
     * Ajoute une tortue côté serveur puis client
     * 1 - on crée une tortue
     * 2 - on prévient le serveur de cette création
     * 3 - selon sa réponse, on ajoute ou non la tortue côté client
     */
    public void addTortueServeurAndClient(){

        int x = Math.round(MainEntryPoint.UNTERRAIN.getLongueur()/2) - 25; //largeur image/2
        int y = Math.round(MainEntryPoint.UNTERRAIN.getLargeur()/2) - 25; //hauteur image/2
        tmpTortue = new Tortue("Tortue-", x, y);


        GWTServiceAsync svc = (GWTServiceAsync) GWT.create(GWTService.class);

        final AsyncCallback<String> callback = new AsyncCallback<String>() {

            public void onSuccess(String result) {
                addTortueClient(result);
		MainEntryPoint.MESSAGES.setText(tmpTortue.getNom() + " ajoutée côté Client et Serveur.");
            }

            public void onFailure(Throwable caught) {
                MainEntryPoint.MESSAGES.setText("Serveur erreur : Ajout de Tortue Echoué (l'ajout côté client a aussi été annulé).");
            }
        };

        svc.addTortue(MainEntryPoint.IDCLIENT, MainEntryPoint.MESTORTUES.size(), tmpTortue, callback);

    }


    /**
     * Seulement après que le serveur ai ajouté la tortue, celle-ci est ajouté puis affiché côté client.
     * @param nomTortue le nom renvoyé par le serveur
     */
    private void addTortueClient(String nomTortue){

        tmpTortue.setNom(nomTortue);
        MainEntryPoint.MESTORTUES.add(tmpTortue); //Ajout au Modèle
        Outils.tortueCourante = tmpTortue; //Mise à jour de la tortue courante

        //Création d'une commande pour la tortue qui va être affichée dans le menu
        Command majTortueCourante = new ItemTortueCommand(tmpTortue);

        //Ajout d'un Item au menu de la Vue
        MenuItem itemTortue = new MenuItem(tmpTortue.getNom(), majTortueCourante);
        menuDesTortues.addItem(itemTortue);

        VueTerrain.addTortueTerrain(MainEntryPoint.VUEMESTORTUES, tmpTortue);
    }


}
