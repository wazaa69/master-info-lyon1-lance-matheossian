package org.tortue.client.Vue;

import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Vue de la liste des Tortues
 */
public class ListeTortues extends HTMLPanel {

    /**
     * Crée un conteneur avec id
     * @param id l'id du conteneur (on se passera de createUniqueId() pour cet APi)
     */
    public ListeTortues(String id) {
        super("<div id='" + id + "'></div>");
        setStyleName("conteneurMesTortues");
        //createUniqueId(); //au vu la taille de l'application on s'en passera
    }

}
