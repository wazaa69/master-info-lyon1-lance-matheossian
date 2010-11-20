package org.tortue.client.Vue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.tortue.client.Modele.Tortue;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
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


    private MainEntryPoint mep;

    private MenuBar menuDesTortues; /** l'identifiant du conteneur */

    private Tortue tmpTortue; /** une tortue temporaire */
    
    /**
     * Crée un conteneur avec id
     * @param id l'id du conteneur (on se passera de createUniqueId() pour cet APi)
     */
    public ListeMesTortues(String id, MainEntryPoint mep) {

        super("");
        getElement().setId(id);

        this.mep = mep;

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

        Label tmpLabel = new Label("Mes tortues : ");
        tmpLabel.setStyleName("titreListe");

        add(tmpLabel, id);

        
        //création et ajout du menu vertical
        menuDesTortues = new MenuBar(true);
        menuDesTortues.getElement().setId("Liste" + id);
        menuDesTortues.getElement().setAttribute("style", "overflow-y:scroll;"); //pas accepté dans la css
        add(menuDesTortues, id);

    }

    /**
     * Ajoute une tortue côté serveur puis client
     * 1 - on crée une tortue
     * 2 - on prévient le serveur de cette création
     * 3 - selon sa réponse, on ajoute ou non la tortue côté client
     */
    public void addTortueServeurAndClient(){

        int x = Math.round(mep.getModele().getUnTerrain().getLongueur()/2) - 25; //largeur image/2
        int y = Math.round(mep.getModele().getUnTerrain().getLargeur()/2) - 25; //hauteur image/2

        tmpTortue = new Tortue("Tortue-", x, y);

        GWTServiceAsync svc = (GWTServiceAsync) GWT.create(GWTService.class);

        final AsyncCallback<String> callback = new AsyncCallback<String>() {

            public void onSuccess(String result) {
                addTortueClient(result);
		MainEntryPoint.INFOMESS.setText(tmpTortue.getNom() + " ajoutée côté Client et Serveur.");
            }

            public void onFailure(Throwable caught) {
                MainEntryPoint.INFOMESS.setText("Serveur erreur : Ajout de Tortue Echoué (l'ajout côté client a aussi été annulé).");
            }
        };

        svc.addTortue(mep.getIdClient(), mep.getModele().getMesTortues().size(), tmpTortue, callback);

    }


    /**
     * Seulement après que le serveur ai ajouté la tortue, celle-ci est ajouté puis affiché côté client.
     * @param nomTortue le nom renvoyé par le serveur
     */
    private void addTortueClient(String nomTortue){

        tmpTortue.setNom(nomTortue);
        mep.getModele().getMesTortues().add(tmpTortue); //Ajout au Modèle
        Outils.tortueCourante = tmpTortue; //Mise à jour de la tortue courante

        //Création d'une commande pour la tortue qui va être affichée dans le menu
        Command majTortueCourante = new ItemTortueCommand(tmpTortue);

        //Ajout d'un Item au menu de la Vue
        MenuItem itemTortue = new MenuItem(tmpTortue.getNom(), majTortueCourante);
        menuDesTortues.addItem(itemTortue);

        mep.getVueTerrain().addTortueTerrain(mep.getVueMesTortues(), tmpTortue);
    }


}
