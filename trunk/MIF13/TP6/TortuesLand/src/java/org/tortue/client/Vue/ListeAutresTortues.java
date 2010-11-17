package org.tortue.client.Vue;

import com.google.gwt.user.client.ui.HTMLPanel;
import java.util.ArrayList;
import org.tortue.client.Modele.Tortue;

/**
 * Vue de la liste des Tortues
 */
public class ListeAutresTortues extends HTMLPanel {

    private ArrayList<Tortue> autresTortues = new ArrayList<Tortue>(); /** la liste des autres tortues des Clients */

    /**
     * Crée un conteneur avec id
     * @param id l'id du conteneur (on se passera de createUniqueId() pour cet APi)
     */
    public ListeAutresTortues(String id) {

        super("<div id='" + id + "'></div>");
        setStyleName("conteneur"+id);

    }

}
