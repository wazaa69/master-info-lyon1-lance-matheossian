/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yournamehere.client.Message;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.yournamehere.client.ClickBouton;

/**
 * Cette classe sera instancié et sera un composant de notre page HTML
 */
public class formCommunication extends FormPanel {

    //on conserve les champs et labels utiles
    private Label texteUtilisateur;
    private TextBox champsUtilisateur = new TextBox();

    //on créer le contenu du formulaire
    public formCommunication(Label texteUtilisateur) {

        //comme le cadre de réponse ne se trouve as dans le formulaire, on le récupère
        this.texteUtilisateur = texteUtilisateur;

        setEncoding(FormPanel.ENCODING_MULTIPART);
        setMethod(FormPanel.METHOD_POST);


        //création du bouton et de son action
        Button envoyer = new Button("Envoyer");
        envoyer.addClickHandler(new ClickBouton(){
            @Override
            public void onClick(ClickEvent event) {
                getMessage();
            }
        });


        //Organisation dans le formulaire
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(new Label("Entrez un texte : "));
        vPanel.add(champsUtilisateur);
        vPanel.add(envoyer);

        //ajout du contenu, dans le formulaire
        add(vPanel);
    }


    /**
     * Récupère le message renvoyé ar le server et met à jour le champs concerné
     */
    public void getMessage(){

        //définition du service que l'on souhaite appeler
        TraitementAsync svc = (TraitementAsync) GWT.create(Traitement.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) svc;

        //On définit ou trouver le service (son url relative au serveur et au dossier de base)
        String moduleRelativeURL = GWT.getModuleBaseURL() + "message/traitement";
        endpoint.setServiceEntryPoint(moduleRelativeURL);

        /*
         * C'est au travers de l'objet AsyncCallback que le serveur retournera sa réponse au client.
         * On définit par la même occasion le comportement de retour.
         */
        final AsyncCallback<String> callback = new AsyncCallback<String>() {

            //en cas de succes
            public void onSuccess(String result) {
                //On récupère la valeur retournée par le serveur (un String).
                //Et on la met à jour chez le Client.
                texteUtilisateur.setText(result);
            }

            //en cas d'echec
            public void onFailure(Throwable caught) {
                texteUtilisateur.setText("Arg !! Connexion Echouée");
            }
        };

        /*
         * on appelle le service pour récupérer le champs envoyé au serveur
         * on lui envoie le contenu du champs (c'est un pue bête mais c'est pour tester)
         * (par exemple, le message pourrait être stocké sur le server)
         */
        svc.getMessage(champsUtilisateur.getText(), callback);

    }

}
