package org.tortue.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Main entry point.
 */
public class MainEntryPoint implements EntryPoint {
    /** 
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {
    }


    public void onModuleLoad() {
        RootPanel.get().add(creerOutils());
        RootPanel.get().add(creerTerrain());
        RootPanel.get().add(creerListeMesTortues());
    }

    private HTMLPanel creerOutils(){

        HTMLPanel outilsHTML = new HTMLPanel("<div id='outils'></div>");
        outilsHTML.setStyleName("conteneurOutils");


        //balise formulaire
        FormPanel formulaire = new FormPanel();
        formulaire.setEncoding(FormPanel.ENCODING_MULTIPART);
        formulaire.setMethod(FormPanel.METHOD_POST);

        //label + input text + boutons
        Label angle = new Label("Angle : ");
        final TextBox angleTodo = new TextBox();
        angleTodo.setSize("70","20");
        Button avancer = new Button("Avancer");
        Button gauche = new Button("Gauche");
        Button droite = new Button("Droite");

        //disposition
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.add(angle);
        hPanel.add(angleTodo);
        hPanel.add(avancer);
        hPanel.add(gauche);
        hPanel.add(droite);

        //ajout dans le formulaire
        formulaire.add(hPanel);

        //ajout dans la div d'id = "outils"
        outilsHTML.add(formulaire, "outils");

        return outilsHTML;

    }

    private HTMLPanel creerTerrain(){

        HTMLPanel terrainHTML = new HTMLPanel("<div id='terrain'></div>");
        terrainHTML.setStyleName("conteneurTerrain");

        terrainHTML.setPixelSize(500, 500);

        return terrainHTML;

    }

    private HTMLPanel creerListeMesTortues(){

        HTMLPanel mesTortuesHTML = new HTMLPanel("<div id='mesTortues'></div>");
        mesTortuesHTML.setStyleName("conteneurMesTortues");

        return mesTortuesHTML;
    }


}
