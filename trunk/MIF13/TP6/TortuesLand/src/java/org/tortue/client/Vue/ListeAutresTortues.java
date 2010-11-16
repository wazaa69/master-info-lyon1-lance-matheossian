package org.tortue.client.Vue;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Vue de la liste des Tortues
 */
public class ListeAutresTortues extends HTMLPanel {

    /**
     * Crée un conteneur avec id
     * @param id l'id du conteneur (on se passera de createUniqueId() pour cet APi)
     */
    public ListeAutresTortues(String id) {

        super("<div id='" + id + "'></div>");
        setStyleName("conteneur"+id);
        

        //balise formulaire
        FormPanel formulaire = new FormPanel();
        formulaire.setEncoding(FormPanel.ENCODING_MULTIPART);
        formulaire.setMethod(FormPanel.METHOD_POST);

        VerticalPanel vPanel = new VerticalPanel();
        Button ajouterTortue = new Button("[+1 Tortue]");
        vPanel.add(ajouterTortue);

        formulaire.add(vPanel);
        
        add(formulaire, id);
    }

}
