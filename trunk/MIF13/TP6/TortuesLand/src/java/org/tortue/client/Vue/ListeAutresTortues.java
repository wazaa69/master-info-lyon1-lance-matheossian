package org.tortue.client.Vue;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;

import org.tortue.client.MainEntryPoint;


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

        //on ajoute un rafraichissement periodique de la liste des autres tortues et de leur affichage
        Timer t = new Timer() {
          public void run() {
              //MainEntryPoint.MESSAGES.setText("test maj");//recupération des dernière tortues déplacées côté client
          }
        };

        t.scheduleRepeating(1000); //rafraichissement toutes les secondes

    }

}
