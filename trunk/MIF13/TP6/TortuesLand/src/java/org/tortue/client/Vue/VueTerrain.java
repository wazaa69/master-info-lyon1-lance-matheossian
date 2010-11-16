package org.tortue.client.Vue;

import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * La vue du terrain
 */
public class VueTerrain extends HTMLPanel {

    /**
     * Crée la vue du terrain à partir des paramètres
     * @param longueur la longeur du terrain
     * @param largeur la largeur du terrain
     */
    public VueTerrain(String id, int longueur, int largeur) {
        super("<div id='" + id +"'></div>");
        setStyleName("conteneur"+id);
        setPixelSize(longueur, largeur);
    }

}
