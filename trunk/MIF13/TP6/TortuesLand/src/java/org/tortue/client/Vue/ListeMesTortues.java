package org.tortue.client.Vue;

import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Vue de la liste des Tortues
 */
public class ListeMesTortues extends HTMLPanel {

    /**
     * Crée un conteneur avec id
     * @param id l'id du conteneur (on se passera de createUniqueId() pour cet APi)
     */
    public ListeMesTortues(String id) {
        super("<div id='" + id + "'></div>");
        setStyleName("conteneur" + id);
    }

}
